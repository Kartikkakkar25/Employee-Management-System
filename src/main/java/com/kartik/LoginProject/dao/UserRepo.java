package com.kartik.LoginProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kartik.LoginProject.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	User findByUsername(String username);
}
