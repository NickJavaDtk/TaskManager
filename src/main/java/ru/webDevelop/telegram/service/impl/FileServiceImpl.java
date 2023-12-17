package ru.webDevelop.telegram.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.webDevelop.telegram.TelegramBotTask;
import ru.webDevelop.telegram.configuration.TelegramConfig;
import ru.webDevelop.telegram.entity.*;
import ru.webDevelop.telegram.service.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    private final BinaryContentDAO contentDAO;
    private final MessageDocumentDAO documentDAO;
    private final PhotoDocumentDAO photoDAO;
    private final TextDocumentDAO textDAO;
    private final UserTelegramDAO userTelegramDAO;

    private final String SALUTE = "Здравствуйте {name} ваш вопрос";
    private final String IMAGINE = "Вы мне не знакомы. Выберите команду /name и представьтесь пожалуйста как к вам можно обращаться";

    @Autowired
    private TelegramConfig telegramConfig;

    @Autowired
    @Lazy
    private TelegramBotTask telegramBotTask;

    public FileServiceImpl(BinaryContentDAO contentDAO, MessageDocumentDAO documentDAO, PhotoDocumentDAO photoDAO, TextDocumentDAO textDAO, UserTelegramDAO userTelegramDAO) {
        this.contentDAO = contentDAO;
        this.documentDAO = documentDAO;
        this.photoDAO = photoDAO;
        this.textDAO = textDAO;
        this.userTelegramDAO = userTelegramDAO;
    }


    @Override
    public MessageDocument getMessageDocument(Message message, TextDocument textDocument, PhotoDocument photoDocument, UserTelegram user) {
        MessageDocument transientMessageDocument = buildTransientMessageDocument(message, textDocument, photoDocument, user);
        return documentDAO.save(transientMessageDocument);

    }

    @Override
    public TextDocument getTextDocument(Message message) {
        TextDocument textDocument = TextDocument.builder()
                .text(message.getText( ) != null ? message.getText( ) : null)
                .message(message.getCaption( ) != null ? message.getCaption( ) : null)
                .chatId(message.getChatId( ))
                .build();
        return textDAO.save(textDocument);
    }

    private MessageDocument buildTransientMessageDocument(Message message, TextDocument textDocument, PhotoDocument photoDocument, UserTelegram user) {
        MessageDocument messageDocument = new MessageDocument();
        if (message.getChatId().equals(textDocument.getChatId())) {
            List<TextDocument> documentList = new ArrayList<>();
            documentList.add(textDocument);
            messageDocument.setTextDocuments(documentList);
        } else {
            messageDocument.getTextDocuments().add(textDocument);
        }
        if (message.getChatId().equals(photoDocument.getChatId( ))) {
            List<PhotoDocument> photoList = new ArrayList<>();
            photoList.add(photoDocument);
            messageDocument.setListDocuments(photoList);
        } else {
            messageDocument.getListDocuments().add(photoDocument);
        }
        messageDocument.setUser(user);
        return messageDocument;
    }

    @Override
    public PhotoDocument getPhotoDocument(Message message) {
        Long photoSizeCount = Long.valueOf(message.getPhoto().size());
        int photoIndex = photoSizeCount > 1 ? message.getPhoto().size() - 1 : 0;
        PhotoSize photo = message.getPhoto().get(photoIndex);
        String fileId = photo.getFileId();
        ResponseEntity<String> response = getFilePath(fileId);
        if (response.getStatusCode() == HttpStatus.OK) {
            BinaryContent persistentBinary = getPersistentBinaryContent(response);
            PhotoDocument  rawPhotoTransient = buildTransientAppPhoto(photo, persistentBinary);
            PhotoDocument photoTransient =  rawPhotoTransient.builder().chatId(message.getChatId( )).build();
            return photoDAO.save(photoTransient);
        } else {
            throw new RuntimeException("Это из фото");
        }
    }

    private ResponseEntity<String> getFilePath(String fileId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(
                telegramConfig.getUri(),
                HttpMethod.GET,
                request,
                String.class,
                telegramConfig.getToken(),
                fileId

        );
    }

    private PhotoDocument buildTransientAppPhoto(PhotoSize photo, BinaryContent persistentBinary) {
        return PhotoDocument.builder()
                .fileId(photo.getFileId( ))
                .content(persistentBinary)
                .build();


    }

    private BinaryContent getPersistentBinaryContent(ResponseEntity<String> response) {
        String filePath = getFilePath(response);
        byte[] array = downloadFile(filePath);
        BinaryContent binary = BinaryContent.builder( )
                .content(array)
                .build( );
    return contentDAO.save(binary);
    }

    private byte[] downloadFile(String filePath) {
        String fullURI = telegramConfig.getStorage()
                .replace("{token}", telegramConfig.getToken())
                .replace("{filePath}", filePath);
        URL urlObj = null;
        try {
            urlObj = new URL(fullURI);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try (InputStream is = urlObj.openStream()) {
            return is.readAllBytes( );
        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;
    }

    private String getFilePath(ResponseEntity<String> response) {
        JSONObject jsonObject = new JSONObject(response.getBody());
        return String.valueOf(jsonObject
                .getJSONObject("result")
                .getString("file_path"));
    }

    public UserTelegram getUserTelegramOrSave(Message message) {
        Long userId = message.getFrom().getId();
        UserTelegram userTelegram = userTelegramDAO.findUserTelegramByTelegramUserId(userId);
        if (userTelegram == null) {
            getFriendlyNoName(message);
            UserTelegram transientUser = getFriendlyNoNameInput(message);
            return userTelegramDAO.save(transientUser);
        } else {
            saluteName(message);
        }
        return userTelegram;

    }

    private void saluteName(Message message) {
        String name = message.getText();
        String senderText = SALUTE.replace("{name}", name);
        SendMessage sendMessage = new SendMessage(String.valueOf(message.getChatId()), senderText );
        try {
            telegramBotTask.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void getFriendlyNoName(Message message ) {
        SendMessage sendMessage = new SendMessage(String.valueOf(message.getChatId()), IMAGINE );
        try {
            telegramBotTask.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private UserTelegram getFriendlyNoNameInput(Message message) {
        String name = message.getText();
        String senderText = SALUTE.replace("{name}", name);
        SendMessage sendMessage = new SendMessage(String.valueOf(message.getChatId()), senderText );
        try {
            telegramBotTask.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        return UserTelegram.builder( )
                .telegramUserId(message.getFrom().getId())
                .firstName(message.getChat().getFirstName())
                .friendlyName(name)
                .build( );
    }


}
