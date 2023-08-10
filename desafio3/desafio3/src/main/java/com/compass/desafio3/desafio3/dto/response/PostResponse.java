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
    private List<Comment> comments;
    private List<History> history;

    public static List<PostResponse> fromPosts(List<Post> posts) {
        List<PostResponse> postResponses = new ArrayList<>();

        for (Post post : posts) {
            PostResponse postResponse = new PostResponse();
            postResponse.setId(post.getPostId());
            postResponse.setTitle(post.getTitle());
            postResponse.setBody(post.getBody());
            postResponse.setState(post.getState());
            postResponse.setComments(post.getComments());
            postResponse.setHistory(post.getHistory());

            postResponses.add(postResponse);
        }

        return postResponses;
    }

}
