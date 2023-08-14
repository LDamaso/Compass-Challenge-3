package com.compass.desafio3.desafio3.entity;


import com.compass.desafio3.desafio3.enums.PostState;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Comment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Post_id")
    private Post post;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;




    @Column(name = "body")
    private String body;

}
