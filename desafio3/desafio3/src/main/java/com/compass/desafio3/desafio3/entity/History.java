package com.compass.desafio3.desafio3.entity;


import com.compass.desafio3.desafio3.enums.PostState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "History")
@Entity
@Getter
@Setter
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    private Post post;

    @Column(name = "state")
    private PostState state;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

}
