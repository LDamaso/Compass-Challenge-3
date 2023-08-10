package com.compass.desafio3.desafio3.entity;


import com.compass.desafio3.desafio3.enums.PostState;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


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
    private Long postId;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Enumerated(EnumType.STRING)
    private PostState state;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;


    @OneToMany(mappedBy = "post" ,cascade = CascadeType.ALL)
    private List<History> histories;


    public List<History> getHistory(){
        return histories;
    }

    public List<Comment> getComments(){
        return comments;
    }

    public void setComments(List<Comment> comments){
        this.comments = comments;
    }

    public void setHistory(List<History> histories){
        this.histories = histories;
    }




}
