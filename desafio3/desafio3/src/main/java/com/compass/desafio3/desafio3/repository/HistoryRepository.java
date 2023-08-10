package com.compass.desafio3.desafio3.repository;

import com.compass.desafio3.desafio3.entity.History;
import com.compass.desafio3.desafio3.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History,Long> {

}
