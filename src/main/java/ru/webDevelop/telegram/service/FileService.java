package ru.webDevelop.telegram.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.webDevelop.telegram.entity.*;

public interface FileService {
    MessageDocument getMessageDocument(Message message, TextDocument textDocument, PhotoDocument photoDocument, UserTelegram userTelegram);
    TextDocument getTextDocument(Message message);
    PhotoDocument getPhotoDocument(Message message);
}
