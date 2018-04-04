package hu.unideb.inf.szakdoga.controller;

import hu.unideb.inf.szakdoga.model.Room;
import hu.unideb.inf.szakdoga.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Room> getRoom() {
        return new ResponseEntity<>(roomService.getRoom(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createRoom() {
        roomService.createRoom();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.POST, path = "/quit")
    public ResponseEntity<Void> quitRoom() {
        roomService.quit();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, path="/getall")
    public ResponseEntity<List<Room>> getRooms() {
        return new ResponseEntity<>(roomService.getAllRooms(), HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.POST, path = "/{roomId}")
    public ResponseEntity<Void> joinRoom(@PathVariable Long roomId) {
        roomService.joinRoom(roomId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.PATCH, path="/ready")
    public ResponseEntity<Void> changeReadyState() {
        roomService.toggleReady();
        return new ResponseEntity<>(HttpStatus.OK);
    }
 }
