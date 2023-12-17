package ru.webDevelop.telegram.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.webDevelop.telegram.entity.MessageDocument;

public interface MessageDocumentDAO extends JpaRepository<MessageDocument, Long> {
}
