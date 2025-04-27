package com.kartik.LoginProject.service;

import org.springframework.stereotype.Service;
import com.kartik.LoginProject.model.Roster;
import com.kartik.LoginProject.model.User;

import jakarta.transaction.Transactional;

import com.kartik.LoginProject.dao.RosterRepository;

@Service
public class RosterService {

    private final RosterRepository rosterRepository;

    public RosterService(RosterRepository rosterRepository) {
        this.rosterRepository = rosterRepository;
    }

    public void saveRoster(Roster roster) {
    	roster.setStatus("Active");
        rosterRepository.save(roster);
    }
    @Transactional  // ✅ Ensures database commit
    public void updateRoster(Roster roster) {
        rosterRepository.save(roster);
    }
    public Roster getRosterByUser(User user) {
        return rosterRepository.findByUser(user);
    }
}
