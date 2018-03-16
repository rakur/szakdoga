package hu.unideb.inf.szakdoga.controller;

import hu.unideb.inf.szakdoga.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class BaseController {
  //  @Autowired
    //GameService gameService;
/*
    @RequestMapping(method = RequestMethod.GET, path = "/test")
    public String test() {
        return "asd";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/newgame")
    public String putGame() {
        gameService.create();
        return "game created";
    }
    @RequestMapping(method = RequestMethod.GET, path="/get")
    public String getGame() {
        StringBuilder sb = new StringBuilder();
        gameService.getAll().forEach(game -> sb.append(game.toString()));
        return sb.toString();
    }*//*
    @GetMapping(path = "/index")
    public String loadIndexhtml() {
        return "index";
    }*/
}
