package com.compass.desafio3.desafio3.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @ManyToOne
    private Post postId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;



    @Column(name = "body")
    private String body;

}
