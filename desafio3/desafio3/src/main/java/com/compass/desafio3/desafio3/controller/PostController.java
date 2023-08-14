package com.compass.desafio3.desafio3.controller;


import com.compass.desafio3.desafio3.dto.response.CommentDTO;
import com.compass.desafio3.desafio3.dto.response.HistoryDTO;
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
import java.util.ArrayList;
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
        post.setId(postId);
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
        postRepository.save(post);
        History history = new History();
        history.setPost(post);
        history.setState(post.getState());
        history.setTimestamp(LocalDateTime.now());


        historyRepository.save(history);

        return ResponseEntity.ok("Post processing completed.");
    }

    @PostMapping("/process-posts")
    public ResponseEntity<String> processAllPosts() {
        for (Long postId = 1L; postId <= 100L; postId++) {
            processPost(postId);
        }

        return ResponseEntity.ok("Processing of all posts completed.");
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<String> processComments(@PathVariable Long postId) {

        if (!postRepository.existsById(postId)) {
            return ResponseEntity.notFound().build();
        }


        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        String commentsApiUrl = "https://jsonplaceholder.typicode.com/comments?postId=" + postId;
        Comment[] commentsArray = restTemplate.getForObject(commentsApiUrl, Comment[].class);
        List<Comment> comments = Arrays.asList(commentsArray);


        for (Comment comment : comments) {
            comment.setPost(post);
        }


        commentRepository.saveAll(comments);


        post.setState(PostState.COMMENTS_FIND);

        post.setState(PostState.COMMENTS_OK);

        postRepository.save(post);

        if (post.getState() != PostState.COMMENTS_OK){
            post.setState(PostState.FAILED);

            postRepository.save(post);
            post.setState(PostState.DISABLED);

        }else {
            post.setState(PostState.ENABLED);
        }


        History history = new History();
        history.setPost(post);
        history.setState(post.getState());
        history.setTimestamp(LocalDateTime.now());
        historyRepository.save(history);

        return ResponseEntity.ok("Comments processing completed for post with ID " + postId);
    }


    @PostMapping("/process-comments")
    public ResponseEntity<String> processAllComments() {
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            processComments(post.getId());
        }

        return ResponseEntity.ok("Processing of comments for all posts completed.");
    }



    @DeleteMapping("/disable/{postId}")
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


    @PutMapping("/enable/{postId}")
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

        post.setState(PostState.ENABLED);
        postRepository.save(post);

        history.setPost(post);
        history.setState(post.getState());
        history.setTimestamp(LocalDateTime.now());

        return ResponseEntity.ok("Post reprocessed");
    }

    @GetMapping("/query")
    public ResponseEntity<List<PostResponse>> queryPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> result = new ArrayList<>();

        for (Post post : posts) {
            PostResponse postResponse = new PostResponse();
            postResponse.setId(post.getId());
            postResponse.setTitle(post.getTitle());
            postResponse.setBody(post.getBody());
            postResponse.setState(post.getState());

            List<Comment> comments = commentRepository.findByPost(post);
            List<CommentDTO> commentDTOs = new ArrayList<>();
            for (Comment comment : comments) {
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setId(comment.getId());
                commentDTO.setBody(comment.getBody());
                commentDTOs.add(commentDTO);
            }
            postResponse.setComments(commentDTOs);

            List<History> histories = historyRepository.findByPost(post);
            List<HistoryDTO> historyDTOs = new ArrayList<>();
            for (History history : histories) {
                HistoryDTO historyDTO = new HistoryDTO();
                historyDTO.setId(history.getId());
                historyDTO.setDate(history.getTimestamp());
                historyDTO.setState(history.getState());
                historyDTOs.add(historyDTO);
            }
            postResponse.setHistory(historyDTOs);

            result.add(postResponse);
        }

        return ResponseEntity.ok(result);
    }



}
