package com.ewersson.dslist.controller;

import com.ewersson.dslist.dto.GameListDTO;
import com.ewersson.dslist.dto.GameMinDTO;
import com.ewersson.dslist.dto.ReplacementDTO;
import com.ewersson.dslist.service.GameListService;
import com.ewersson.dslist.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lists")
public class GameListController {

    @Autowired
    private GameListService gameListService;

    @Autowired
    private GameService gameService;

    @GetMapping("/{list_id}/games")
    public ResponseEntity<List<GameMinDTO>> findByList(@PathVariable Long list_id) {
        List<GameMinDTO> games = gameService.findByList(list_id);
        return ResponseEntity.ok(games);
    }

    @PostMapping("/{listId}/replacement")
    public void move(@PathVariable Long listId, @RequestBody ReplacementDTO body) {
        gameListService.move(listId, body.getSourceIndex(), body.getDestinationIndex());
    }

    @GetMapping
    public ResponseEntity<List<GameListDTO>> findAll() {
        List<GameListDTO> list = gameListService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public GameListDTO findById(@PathVariable Long id){
        GameListDTO list = gameListService.findById(id);
        return ResponseEntity.ok(list).getBody();
    }

}