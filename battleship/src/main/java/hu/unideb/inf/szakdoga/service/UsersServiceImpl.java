package hu.unideb.inf.szakdoga.service;

import hu.unideb.inf.szakdoga.exceptions.UserAlreadyExistsException;
import hu.unideb.inf.szakdoga.model.Users;
import hu.unideb.inf.szakdoga.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public void register(@Valid Users user) {
        if (!usersRepository.existsUserByEmail(user.getEmail()) &&
                !usersRepository.existsUserByUserName(user.getUsername())) {
            usersRepository.save(user);
        } else {
            throw new UserAlreadyExistsException("Username or Email already exists.");
        }
    }

    /*@Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }*/

    @Override
    public Users getUserByUserName(String userName) {
        return usersRepository.findUserByUserName(userName);
    }

    @Override
    public Users getUserById(Long id) {
        return usersRepository.findUserById(id);
    }
}
