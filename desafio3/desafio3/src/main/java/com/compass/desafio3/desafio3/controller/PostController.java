package com.compass.desafio3.desafio3.controller;


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
    public ResponseEntity<String> processPost(@PathVariable Long postId){
            History history = new History();
            String apiUrl = "https://jsonplaceholder.typicode.com/posts/" + postId;
            Post post = restTemplate.getForObject(apiUrl, Post.class);
            post.setState(PostState.CREATED);
            postRepository.save(post);
            if (postRepository.existsById(postId)){
                return ResponseEntity.badRequest().body("Post already exists");
            }
            
            post.setState(PostState.POST_FIND);
            postRepository.save(post);

            if (post == null){
                return ResponseEntity.notFound().build();
            }

            post.setState(PostState.POST_OK);
            postRepository.save(post);

            if (post.getState()==PostState.POST_OK) {
                post.setState(PostState.COMMENTS_FIND);
                String commentsApiUrl = "https://jsonplaceholder.typicode.com/comments?postId=" + postId;
                Comment[] commentsArray = restTemplate.getForObject(commentsApiUrl, Comment[].class);
                List<Comment> comments = Arrays.asList(commentsArray);

                post.setComments(comments);
                postRepository.save(post);


            }else {
                post.setState(PostState.FAILED);

                postRepository.save(post);
            }
            history.setPost(post);
            history.setState(post.getState());
            history.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok("Post  Processing initiated");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> disablePost(@PathVariable int postId){



        return ResponseEntity.ok("Post Disabled");
    }


    @PutMapping("/{postId}")
    public ResponseEntity<String> reprocessPost(@PathVariable int postId){


        return ResponseEntity.ok("Post reprocessed");
    }

    //@GetMapping
    //public ResponseEntity<List<PostResponse>> queryPosts(){



      //  return  ResponseEntity.ok(posts);
    //}



}
