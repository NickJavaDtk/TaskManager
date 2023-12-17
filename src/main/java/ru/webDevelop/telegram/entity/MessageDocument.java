package ru.webDevelop.telegram.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message")
public class MessageDocument {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private UserTelegram user;
    @OneToMany(mappedBy = "documentText")
    private List<TextDocument> textDocuments;
    @OneToMany(mappedBy = "document")
    private List<PhotoDocument> listDocuments;
}
