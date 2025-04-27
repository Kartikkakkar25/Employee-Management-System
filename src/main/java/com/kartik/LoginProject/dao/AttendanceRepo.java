package com.kartik.LoginProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kartik.LoginProject.model.Attendance;
import com.kartik.LoginProject.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByUser(User user);
    
    Optional<Attendance> findByUserAndSubmissionTimestampBetween(
            User user,
            LocalDateTime startOfDay,
            LocalDateTime endOfDay
    );
    Optional<Attendance> findByUserAndSubmissionTimestamp(
            User user,
            LocalDateTime startOfDay
    );

    List<Attendance> findAllByUserAndSubmissionTimestampBetween(
            User user,
            LocalDateTime startOfDay,
            LocalDateTime endOfDay
    );
} 
