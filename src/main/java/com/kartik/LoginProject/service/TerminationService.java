package com.kartik.LoginProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kartik.LoginProject.dao.TerminationRepo;
import com.kartik.LoginProject.model.Roster;
import com.kartik.LoginProject.model.Termination;
import com.kartik.LoginProject.model.User;

@Service
public class TerminationService {

	@Autowired
	TerminationRepo termRepo;
	
	public void saveTermination(Termination term, Roster roster) {
		roster.setStatus("Inactive");
		term.setName((roster != null) ? roster.getFullName() : term.getUser().getUsername());
			termRepo.save(term);
	}
	
	public Termination getTerminationByUser(User user) {
		return termRepo.findByUser(user);
	}
}
