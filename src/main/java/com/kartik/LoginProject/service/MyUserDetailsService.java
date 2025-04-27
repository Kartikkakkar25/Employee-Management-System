package com.kartik.LoginProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kartik.LoginProject.dao.UserRepo;
import com.kartik.LoginProject.model.User;
import com.kartik.LoginProject.model.UserPrincipal;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = repo.findByUsername(username);
		if(user==null) {
			System.out.println("User 404");
			throw new UsernameNotFoundException("User 404");
		}
		return new UserPrincipal(user);
	}

	public void updatePassword(String username, String newPassword) throws Exception{
		User user = repo.findByUsername(username);
		if(user==null) {
			System.out.println("User 404");
			throw new UsernameNotFoundException("User 404");
		}
		user.setPassword(newPassword);
		repo.save(user);

	}
	public boolean existsByUsername(String username) {
		User u = new User();
		u=repo.findByUsername(username);
        if(u==null) {
        	return false;
        }
        else {
        	return true;
        }
    }
	public void saveUser(User user) {
		user.setRole("ROLE_USER");
		repo.save(user);
	}
	public User findByUsername(String username) {
		User u = new User();
		u=repo.findByUsername(username);
		return u;
	}

}
