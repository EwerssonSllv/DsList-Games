package com.ewersson.dslist.controller;

import com.ewersson.dslist.dto.GameMinDTO;
import com.ewersson.dslist.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public ResponseEntity<List<GameMinDTO>> findAll() {
        List<GameMinDTO> games = gameService.findAll();
        return ResponseEntity.ok(games);
    }


}
