package com.compass.desafio3.desafio3.controller;


import com.compass.desafio3.desafio3.dto.response.PostResponse;
import com.compass.desafio3.desafio3.entity.History;
import com.compass.desafio3.desafio3.entity.Post;
import com.compass.desafio3.desafio3.entity.Comment;
import com.compass.desafio3.desafio3.enums.PostState;
import com.compass.desafio3.desafio3.repository.CommentRepository;
import com.compass.desafio3.desafio3.repository.HistoryRepository;
import com.compass.desafio3.desafio3.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @PostMapping("/{postId}")
    public ResponseEntity<String> processPost(@PathVariable Long postId) {

        if (postId < 1 || postId > 100) {
            return ResponseEntity.badRequest().body("Invalid postId. It must be between 1 and 100.");
        }


        if (postRepository.existsById(postId)) {
            return ResponseEntity.badRequest().body("Post with id " + postId + " already exists.");
        }


        Post post = new Post();
        post.setPostId(postId);
        post.setState(PostState.CREATED);


        postRepository.save(post);


        String postApiUrl = "https://jsonplaceholder.typicode.com/posts/" + postId;
        post = restTemplate.getForObject(postApiUrl, Post.class);

        if (post == null) {
            return ResponseEntity.notFound().build();
        }


        post.setState(PostState.POST_FIND);


        postRepository.save(post);


        post.setState(PostState.POST_OK);
        History history = new History();
        history.setPost(post);
        history.setState(post.getState());
        history.setTimestamp(LocalDateTime.now());


        historyRepository.save(history);

        return ResponseEntity.ok("Post processing completed.");
    }



    @DeleteMapping("/post/disable/{postId}")
    public ResponseEntity<String> disablePost(@PathVariable Long postId){

        if (postId < 1 || postId > 100) {
            return ResponseEntity.badRequest().body("Invalid postId. It must be between 1 and 100.");
        }
        Optional<Post> optionalPost= postRepository.findById(postId);
        if (optionalPost.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Post post = optionalPost.get();
        if (post.getState() != PostState.ENABLED){
            return ResponseEntity.badRequest().body("Post is not in the ENABLED state.");
        }
        post.setState(PostState.DISABLED);

        postRepository.save(post);

        History history = new History();
        history.setPost(post);
        history.setState(post.getState());
        history.setTimestamp(LocalDateTime.now());
        historyRepository.save(history);


        return ResponseEntity.ok("Post Disabled");
    }


    @PutMapping("/post/enable/{postId}")
    public ResponseEntity<String> reprocessPost(@PathVariable Long postId){

        if (postId < 1 || postId > 100) {
            return ResponseEntity.badRequest().body("Invalid postId. It must be between 1 and 100.");
        }
        Optional<Post> optionalPost= postRepository.findById(postId);
        if (optionalPost.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Post post = optionalPost.get();

        if (post.getState() != PostState.ENABLED && post.getState() != PostState.DISABLED) {
            return ResponseEntity.badRequest().body("Post is not in the ENABLED or DISABLED state.");
        }
        post.setState(PostState.UPDATING);

        postRepository.save(post);


        History history = new History();
        history.setPost(post);
        history.setState(post.getState());
        history.setTimestamp(LocalDateTime.now());

        historyRepository.save(history);

        return ResponseEntity.ok("Post reprocessed");
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> queryPosts(){
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = PostResponse.fromPosts(posts);


        return  ResponseEntity.ok(postResponses);
    }



}
