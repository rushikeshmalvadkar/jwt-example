package com.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/games")
@Slf4j
public class GameRestController {

    @GetMapping(value = "/play")
    public String playGame(){
        log.info("<<<< playGame()");
        String greetMessage = "Playing Game";
        log.info("playGame >>>>>");
        return greetMessage;
    }

}
