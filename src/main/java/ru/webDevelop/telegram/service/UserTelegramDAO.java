package ru.webDevelop.telegram.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.webDevelop.telegram.entity.UserTelegram;

public interface UserTelegramDAO extends JpaRepository<UserTelegram, Long> {
    UserTelegram findUserTelegramByTelegramUserId(Long id);
}
