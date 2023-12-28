package ru.webDevelop.telegram.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.webDevelop.telegram.entity.MessageDocument;

import java.util.List;

public interface MessageDocumentDAO extends JpaRepository<MessageDocument, Long> {
    MessageDocument findMessageDocumentByChatIdAndIsActiveTrue(Long id);
    MessageDocument findMessageDocumentById(Long id);

    List<MessageDocument> findAll();
}
