package ru.webDevelop.telegram.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.webDevelop.telegram.entity.BinaryContent;

public interface BinaryContentDAO extends JpaRepository<BinaryContent, Long> {
}
