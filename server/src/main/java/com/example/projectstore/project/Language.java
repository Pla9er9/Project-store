package com.example.projectstore.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "language")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Language {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    private Project project;
    private String name;
    private int filesCount;
}
