package com.compass.desafio3.desafio3.repository;

import com.compass.desafio3.desafio3.entity.Comment;
import com.compass.desafio3.desafio3.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);
}
