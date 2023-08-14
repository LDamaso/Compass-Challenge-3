package com.compass.desafio3.desafio3.dto.response;

import com.compass.desafio3.desafio3.entity.Comment;
import com.compass.desafio3.desafio3.entity.History;
import com.compass.desafio3.desafio3.entity.Post;
import com.compass.desafio3.desafio3.enums.PostState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private Long id;
    private String title;
    private String body;
    private PostState state;

    private List<CommentDTO> comments;
    private List<HistoryDTO> history;



}
