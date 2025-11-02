package com.ewersson.dslist.service;

import com.ewersson.dslist.dto.GameDTO;
import com.ewersson.dslist.dto.GameMinDTO;
import com.ewersson.dslist.entities.Game;
import com.ewersson.dslist.projection.GameMinProjection;
import com.ewersson.dslist.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public Game create(Game game){
        return gameRepository.save(game);
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findByList(Long gameList){
        List<GameMinProjection> games = gameRepository.searchByList(gameList);
        return games.stream().map(GameMinDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findAll(){
        List<Game> games = gameRepository.findAll();
        return games.stream().map(GameMinDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public GameDTO findById(@PathVariable Long id){
       Game game = gameRepository.findById(id).get();
       return new GameDTO(game);
    }

}
