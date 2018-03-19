package hu.unideb.inf.szakdoga.service;

import hu.unideb.inf.szakdoga.model.Users;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Users getCurrentUser();
}
