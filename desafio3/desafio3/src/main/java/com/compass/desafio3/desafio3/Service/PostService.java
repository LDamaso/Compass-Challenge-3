package com.compass.desafio3.desafio3.Service;

import com.compass.desafio3.desafio3.entity.Post;

import java.util.List;

public interface PostService {

    public Post processPost(Long postId);

    public Post disablePost(Long postId);

    public Post reprocessPost(Long postId);

    public List<Post> getAllPosts();




}