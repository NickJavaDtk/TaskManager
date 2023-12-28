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

import java.util.Arrays;
import java.util.List;

@Component
public class TelegramBotTask extends TelegramLongPollingBot {
    @Autowired
    private TelegramConfig config;
    @Autowired
    private FileServiceImpl fileService;

    private final String STARTMESSAGE = "Добро пожаловать в чат бот Телеграмма." + "\n"
            + "Данный бот поможет вам создать обращение" + "\n"
            + "в отдел ИТ. Для того чтобы создать обращение\n"
            + "необходимо ввести команду /start После данной\n"
            + "команды бот поздоровается с вами, далее вы можете\n"
            + "написать текст обращение и прикрепить если\n"
            + "необходимо фотографии. Фотографии необходимо\n"
            + "высылать по одной, если отравляете группой\n"
            + "я вижу только первую левую\n"
            + "Введите /start чтобы начать общение\n"
            + "Введите /end чтобы закрыть обращение\n"
            + "Введите /help чтобы просмотреть список комманд\n";
    private final String ERRORMESSAGE = "Я очень тупой бот - следуйте инструкциям\n"
            + " и я смогу вам помочь. Я в это верю";

    private final String APPEALMESSAGE = "Введите  /appeal чтобы описать суть обращения, если необходимо приложите снимок";

    private final String REGISTRATIONMESSAGE = "Ваше сообщение зарегистрировано под номером {number}. Введите текст обращения";
    private final String ENDMESSAGE = "Сообщение под номером {number} закрыто";


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
        if (update.hasMessage( )) {
            Message messageTelegram = update.getMessage();
            //sendMessages(messageTelegram.getChatId( ), STARTMESSAGE);
            MessageDocument messageDocument = fileService.getMessageDocument(messageTelegram.getChatId());
            if (messageTelegram.hasText() && (messageDocument == null || !messageDocument.getIsActive())) {
                String inputMessage = messageTelegram.getText();
                sendMessages(messageTelegram.getChatId( ), STARTMESSAGE);
                if (checkMessageInput(inputMessage)) {
                    if (TelegramCommands.START.equals(inputMessage )) {
                        UserTelegram user = fileService.getUserTelegramOrSave(messageTelegram);
                        MessageDocument messageAddUser = fileService.createNewMessageDocument(messageTelegram, user);
//                        String registration = REGISTRATIONMESSAGE.replace("{number}", String.valueOf(message.getId( )));
//                        sendMessages(update.getMessage( ).getChatId( ), registration);
                        sendMessageChat(messageTelegram.getChatId(), messageTelegram, messageAddUser);
                    }
                    if (TelegramCommands.HELP.equals(inputMessage )) {
                        help();
                    }
                }
                else {
                    sendMessages(messageTelegram.getChatId(), ERRORMESSAGE);
                }
            } else if ( messageTelegram.hasText() && messageDocument != null && messageDocument.getIsActive()) {
                if (TelegramCommands.END.equals(messageTelegram.getText())) {
                    fileService.closeMessageDocument(messageDocument);
                    sendMessageChat(messageTelegram.getChatId(), messageTelegram, messageDocument);
                } else {
                    fileService.addMessageDocumentTextDocument(messageDocument.getId(), messageTelegram);
                }
            } else if (messageTelegram.hasPhoto() && messageDocument != null && messageDocument.getIsActive()) {
                fileService.addMessageDocumentPhotoDocument(messageDocument.getId(), messageTelegram);
                if (messageTelegram.getCaption().length() != 0) {
                    fileService.addMessageDocumentTextDocument(messageDocument.getId( ), messageTelegram);
                }
            }
            else {
                sendMessages(messageTelegram.getChatId(), "Совсем не понял чего ты хотел");
            }


        }


//        if (update.hasMessage() && update.getMessage().hasText()) {
//            messageAnalysis(update);
//        }
//        else {
//            sendMessages(update.getMessage( ).getChatId( ),  "bsdl dlslds");
//        }
//
//
//
//        Message message = update.getMessage();
//        UserTelegram userTelegram = fileService.getUserTelegramOrSave(message);
//        TextDocument textDocument = null;
//        if (message.getText() != null || message.getCaption() != null) {
//            textDocument = fileService.getTextDocument(message);
//        }
//        PhotoDocument photo = null;
//        if (message.getPhoto() != null) {
//            photo =  fileService.getPhotoDocument(message);
//        }
//       // MessageDocument messageDocument = fileService.getMessageDocument(message, textDocument, photo);
//
//        String s = null;

    }



    private void messageAnalysis(Update update) {
//        while (true) {
//            MessageDocument document = fileService.getMessageDocument(update.getMessage( ).getChatId( ));
//                char char47 = update.getMessage().getText().charAt(1);
//                if (char47 == 47 && document == null) {
//                    sendMessages(update.getMessage( ).getChatId( ), STARTMESSAGE);
//                    String messageText = update.getMessage( ).getText( );
//                    if (TelegramCommands.START.equals(messageText)) {
//                        UserTelegram user = fileService.getUserTelegramOrSave(update.getMessage( ));
//                        MessageDocument message = fileService.createNewMessageDocument(update.getMessage( ), user);
//                        String registration = REGISTRATIONMESSAGE.replace("{number}", String.valueOf(message.getId( )));
//                        sendMessages(update.getMessage( ).getChatId( ), registration);
//                        try {
//                            Thread.sleep(1000);
//                            sendMessages(update.getMessage( ).getChatId( ), APPEALMESSAGE);
//                            break;
//                        } catch (InterruptedException e) {
//                            e.printStackTrace( );
//                        }
//
//                    } else if (TelegramCommands.HELP.equals(messageText)) {
//                        help( );
//                        break;
//                    }
//                } else {
//                    sendMessages(update.getMessage( ).getChatId( ), ERRORMESSAGE);
//                   continue;
//                }
//
//        }

//        if (update.hasMessage() && update.getMessage().hasText()) {
//            sendMessages(update.getMessage().getChatId(), STARTMESSAGE);
//            String messageText = update.getMessage().getText();
//            label1:
//            {
//                if (TelegramCommands.START.equals(messageText)) {
//                    sendMessages(update.getMessage( ).getChatId( ), APPEALMESSAGE);
//                    UserTelegram userTelegram = fileService.getUserTelegramOrSave(update.getMessage( ));
//                    if (TelegramCommands.APPEAL.equals(messageText)) {
//                        MessageDocument document = fileService.createNewMessageDocument(update.getMessage( ), userTelegram);
//                        String registration = REGISTRATIONMESSAGE.replace("{number}", String.valueOf(document.getId( )));
//                        sendMessages(update.getMessage( ).getChatId( ), registration);
//
//                        label2:
//                        {
//                            if (update.getMessage( ).getText( ) != null || update.getMessage( ).getCaption( ) != null) {
//                                TextDocument textDocument = fileService.getTextDocument(update.getMessage( ));
//                                fileService.addListTextDocument(document.getId( ), textDocument);
//                                break label2;
//
//                            }
//                            if (update.getMessage( ).getPhoto( ) != null) {
//                                PhotoDocument photo = fileService.getPhotoDocument(update.getMessage( ));
//                                break label2;
//                            }
//
//                        }
//
//
////                    while (!TelegramCommands.END.equals(messageText )) {
////
////                    }
//                    }
//
//
//                }
//                else {
//                    sendMessages(update.getMessage().getChatId(), "Не понял команды\n Введите /start чтобы начать общение\n");
//                    break label1;
//                };
//            }
//
////            else if (TelegramCommands.APPEAL.equals(messageText )) {
////                UserTelegram userTelegram = fileService.getUserTelegramOrSave(update.getMessage());
////            }
////            else {
////                sendMessages(update.getMessage().getChatId(), "Не понял команды");
////            };
//
//        }
//
////        else  {
////            sendMessages(update.getMessage().getChatId(), "Не понял команды");
////        };


    }

    private String help() {
        return "Список доступный комманд\n"
                + "/start начать общение с ботом и зарегистрировать обращение\n"
                + "/end чтобы закрыть обращение\n"
                + "/help показать список доступных комманд";
    }

    private void sendMessages(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(  );
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        try {
            sendMessagesDelay();
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    private void sendMessagesDelay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean checkMessageInput(String messageText) {
        return Arrays.stream(TelegramCommands.values()).anyMatch(command -> command.equals(messageText));
    }

    private void sendMessageChat(Long chatId, Message message, MessageDocument messageDocument) {
        if (TelegramCommands.END.equals(message.getText() )) {
            String endMessage = ENDMESSAGE.replace("{number}", String.valueOf(messageDocument.getId( )));
            sendMessages(chatId, endMessage);
        } else {
            String registration = REGISTRATIONMESSAGE.replace("{number}", String.valueOf(messageDocument.getId( )));
            sendMessages(chatId, registration);
        }
    }


}
