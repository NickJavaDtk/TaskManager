package ru.webDevelop.telegram.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.webDevelop.telegram.entity.TextDocument;

public interface TextDocumentDAO extends JpaRepository<TextDocument, Long> {
}
