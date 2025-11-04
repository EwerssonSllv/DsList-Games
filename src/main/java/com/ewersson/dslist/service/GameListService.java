package com.ewersson.dslist.service;

import com.ewersson.dslist.dto.GameListDTO;
import com.ewersson.dslist.entities.GameList;
import com.ewersson.dslist.projection.GameMinProjection;
import com.ewersson.dslist.repository.GameListRepository;
import com.ewersson.dslist.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class GameListService {

    @Autowired
    private GameListRepository gameListRepository;

    @Autowired
    private GameRepository gameRepository;

    @Transactional
    public void move(Long listId, int sourceIndex, int destinationIndex) {

        List<GameMinProjection> list = gameRepository.searchByList(listId);

        GameMinProjection obj = list.remove(sourceIndex);
        list.add(destinationIndex, obj);

        for (int i = 0; i < list.size(); i++) {
            gameListRepository.updateBelongingPosition(listId, list.get(i).getId(), i);
        }
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