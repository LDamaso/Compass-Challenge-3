package com.compass.desafio3.desafio3.dto.response;

import com.compass.desafio3.desafio3.enums.PostState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDTO {

    private Long id;
    private LocalDateTime date;
    private PostState state;

}
