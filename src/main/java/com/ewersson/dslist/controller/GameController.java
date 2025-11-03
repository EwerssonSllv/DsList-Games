package com.ewersson.dslist.controller;

import com.ewersson.dslist.dto.GameDTO;
import com.ewersson.dslist.dto.GameMinDTO;
import com.ewersson.dslist.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public ResponseEntity<List<GameMinDTO>> findAll() {
        List<GameMinDTO> games = gameService.findAll();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{id}")
    public GameDTO findById(@PathVariable Long id){
        GameDTO game = gameService.findById(id);
        return ResponseEntity.ok(game).getBody();
    }

}
