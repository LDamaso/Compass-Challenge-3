package com.compass.desafio3.desafio3.entity;


import com.compass.desafio3.desafio3.enums.PostState;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "post")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tittle")
    private String tittle;

    @Column(name = "body")
    private String body;

    @Enumerated(EnumType.STRING)
    private PostState state;



}
