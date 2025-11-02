package com.ewersson.dslist.service;

import com.ewersson.dslist.dto.GameMinDTO;
import com.ewersson.dslist.entities.Game;
import com.ewersson.dslist.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<GameMinDTO> findAll(){
        List<Game> games = gameRepository.findAll();
        List<GameMinDTO> dto = games.stream().map(x -> new GameMinDTO(x)).toList();
        return dto;
    }

}
