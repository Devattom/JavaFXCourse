package org.project.javafxcourse.models.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "history",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title", "showType"}))
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
    @Column(name = "consulted_at", nullable = false)
    private LocalDateTime consultedAt;

    public History() {}

    public History(String title, String showType) {
        this.title = title;
        this.showType = showType;
        this.consultedAt = LocalDateTime.now();
    }

    public void updateConsultedAt() {
        this.consultedAt = LocalDateTime.now();
    }

}