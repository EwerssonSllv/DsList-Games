package com.ewersson.dslist.service;

import com.ewersson.dslist.dto.GameListDTO;
import com.ewersson.dslist.entities.GameList;
import com.ewersson.dslist.repository.GameListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class GameListService {

    @Autowired
    private GameListRepository gameListRepository;

    public GameList create(GameList gameList){
        return gameListRepository.save(gameList);
    }

    @Transactional(readOnly = true)
    public GameListDTO findById(@PathVariable Long id){
        GameList gameList = gameListRepository.findById(id).get();
        return new GameListDTO(gameList);
    }

    @Transactional(readOnly = true)
    public List<GameListDTO> findAll(){
        List<GameList> gameList = gameListRepository.findAll();
        return gameList.stream().map(GameListDTO::new).toList();
    }

}
