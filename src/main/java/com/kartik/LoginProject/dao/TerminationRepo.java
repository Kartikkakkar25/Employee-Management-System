package com.kartik.LoginProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kartik.LoginProject.model.Termination;
import com.kartik.LoginProject.model.User;

@Repository
public interface TerminationRepo extends JpaRepository<Termination, Integer> {
	Termination findByUser(User user);
}
