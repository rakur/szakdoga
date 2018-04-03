package hu.unideb.inf.szakdoga.controller;

import hu.unideb.inf.szakdoga.model.Users;
import hu.unideb.inf.szakdoga.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest//register")
@Slf4j
public class RegisterController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> register(@RequestBody Users user) {
        usersService.register(user);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
