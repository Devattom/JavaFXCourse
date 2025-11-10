package org.project.javafxcourse.models.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "history")
public class History {

    // Getters et setters
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false)
    private String title;

    @Getter
    @Column(nullable = false)
    private String showType;

    @Getter
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public History() {}

    public History(String title, String showType) {
        this.title = title;
        this.showType = showType;
        this.createdAt = LocalDateTime.now();
    }

}