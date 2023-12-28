package ru.webDevelop.telegram.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inputMessage")
public class MessageDocument {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime createDate;
    private Long chatId;
    @ManyToOne
    private UserTelegram user;
    @OneToMany(mappedBy = "documentText", fetch = FetchType.EAGER)
    private List<TextDocument> textDocuments;
    @OneToMany(mappedBy = "document", fetch = FetchType.EAGER)
    private List<PhotoDocument> listDocuments;
    private Boolean isActive;
}
