package ru.webDevelop.telegram.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TextDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Long chatId;
    @ManyToOne
    @JoinColumn(name = "message_id")
    private MessageDocument documentText;
}
