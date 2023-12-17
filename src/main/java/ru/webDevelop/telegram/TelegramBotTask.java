package ru.webDevelop.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.webDevelop.telegram.configuration.TelegramConfig;
import ru.webDevelop.telegram.entity.MessageDocument;
import ru.webDevelop.telegram.entity.PhotoDocument;
import ru.webDevelop.telegram.entity.TextDocument;
import ru.webDevelop.telegram.entity.UserTelegram;
import ru.webDevelop.telegram.enums.TelegramCommands;
import ru.webDevelop.telegram.service.impl.FileServiceImpl;

@Component
public class TelegramBotTask extends TelegramLongPollingBot {
    @Autowired
    private TelegramConfig config;
    @Autowired
    private FileServiceImpl fileService;

    private final String STARTMESSAGE = "Добро пожаловать в чат бот Телеграмма." + "\n"
            + "Данный бот поможет вам создать обращение" + "\n"
            + "в отдел ИТ.\n" + "Введите /start чтобы начать общение\n"
            + "Введите /appeal чтобы зарегистрировать сообщение\n"
            + "Введите /help чтобы просмотреть список комманд\n";

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken( );
    }

    @Override
    public void onUpdateReceived(Update update) {
        messageAnalysis(update);

        Message message = update.getMessage();
        UserTelegram userTelegram = fileService.getUserTelegramOrSave(message);
        TextDocument textDocument = null;
        if (message.getText() != null || message.getCaption() != null) {
            textDocument = fileService.getTextDocument(message);
        }
        PhotoDocument photo = null;
        if (message.getPhoto() != null) {
            photo =  fileService.getPhotoDocument(message);
        }
       // MessageDocument messageDocument = fileService.getMessageDocument(message, textDocument, photo);

        String s = null;

    }



    private void messageAnalysis(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            sendMessages(update.getMessage().getChatId(), STARTMESSAGE);
            String messageText = update.getMessage().getText();
            if (TelegramCommands.START.equals(messageText)) {
                sendMessages(update.getMessage().getChatId(), STARTMESSAGE);
            } else if (TelegramCommands.APPEAL.equals(messageText )) {
                UserTelegram userTelegram = fileService.getUserTelegramOrSave(update.getMessage());
            }
            else {
                sendMessages(update.getMessage().getChatId(), "Не понял команды");
            };

        } else {
            sendMessages(update.getMessage().getChatId(), "Не понял команды");
        };


    }

    private String help() {
        return "Список доступный комманд\n"
                + "/start начать общение с ботом\n"
                + "/appeal зарегистрировать обращение\n"
                + "/help показать список доступных комманд";
    }

    private void sendMessages(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(  );
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }






}
