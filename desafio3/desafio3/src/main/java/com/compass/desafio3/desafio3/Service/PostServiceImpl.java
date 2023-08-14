package com.compass.desafio3.desafio3.Service;




import com.compass.desafio3.desafio3.entity.History;
import com.compass.desafio3.desafio3.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.compass.desafio3.desafio3.repository.HistoryRepository;

import java.time.LocalDateTime;


@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private HistoryRepository historyRepository;

    public History saveLog(Post post){
        History history = new History();
        history.setPost(post);
        history.setState(post.getState());
        history.setTimestamp(LocalDateTime.now());


        return history;
    }

}
