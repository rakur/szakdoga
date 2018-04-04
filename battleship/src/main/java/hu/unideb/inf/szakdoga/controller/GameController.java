package hu.unideb.inf.szakdoga.controller;

import hu.unideb.inf.szakdoga.exceptions.InvalidPlacingPositionException;
import hu.unideb.inf.szakdoga.exceptions.InvalidShootingPositionException;
import hu.unideb.inf.szakdoga.model.Game;
import hu.unideb.inf.szakdoga.service.GameService;
import hu.unideb.inf.szakdoga.service.RoomService;
import hu.unideb.inf.szakdoga.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Game> getGame() {
        Game game = gameService.getGame();
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path="/place")
    public ResponseEntity<Void> placeShip(@RequestBody int[] coord) throws InvalidPlacingPositionException {
        gameService.placeShip(coord[1],coord[0], coord[2] == 1);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.POST, path = "/shoot")
    public ResponseEntity<Void> shoot(@RequestBody int[] coord) throws InvalidShootingPositionException {
        gameService.shoot(coord[1], coord[0]);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path="/finish")
    public ResponseEntity<Void> finish() {
        gameService.finish();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
