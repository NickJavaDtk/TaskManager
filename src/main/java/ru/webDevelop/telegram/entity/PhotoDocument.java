package ru.webDevelop.telegram.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PhotoDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileId;
    private Long chatId;
    @OneToOne
    private BinaryContent content;
    @ManyToOne
    @JoinColumn(name = "message_id")
    private MessageDocument document;


}
