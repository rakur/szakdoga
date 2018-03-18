package hu.unideb.inf.szakdoga.service;

import hu.unideb.inf.szakdoga.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

	@Autowired
    private UsersService usersService;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Users user = usersService.getUserByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Username not found.");
		}
	    return user;
    }

}
