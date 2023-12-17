package ru.webDevelop.telegram.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
