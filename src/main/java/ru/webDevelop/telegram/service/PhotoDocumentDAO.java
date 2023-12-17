package ru.webDevelop.telegram.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.webDevelop.telegram.entity.PhotoDocument;

public interface PhotoDocumentDAO extends JpaRepository<PhotoDocument, Long> {
}
