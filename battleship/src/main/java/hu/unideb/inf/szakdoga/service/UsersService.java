package hu.unideb.inf.szakdoga.service;

import hu.unideb.inf.szakdoga.model.Users;
import org.springframework.stereotype.Service;

@Service
public interface UsersService {
    void register(Users user);
    //User getUserByEmail(String email);
    Users getUserByUserName(String userName);
    Users getUserById(Long id);
}