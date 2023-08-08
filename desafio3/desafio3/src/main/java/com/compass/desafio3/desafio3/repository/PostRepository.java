package com.compass.desafio3.desafio3.repository;

import com.compass.desafio3.desafio3.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

}
