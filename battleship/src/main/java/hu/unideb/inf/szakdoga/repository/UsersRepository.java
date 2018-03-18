package hu.unideb.inf.szakdoga.repository;

import hu.unideb.inf.szakdoga.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<Users, String> {
    Boolean existsUserByEmail(String email);
    Boolean existsUserByUserName(String userName);
    Users findUserByUserName(String userName);
    Users findUserById(Long id);
}
