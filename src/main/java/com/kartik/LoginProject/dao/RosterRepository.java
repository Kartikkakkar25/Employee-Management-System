package com.kartik.LoginProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kartik.LoginProject.model.Roster;
import com.kartik.LoginProject.model.User;

public interface RosterRepository extends JpaRepository<Roster, Integer> {
	Roster findByUser(User user);
}
