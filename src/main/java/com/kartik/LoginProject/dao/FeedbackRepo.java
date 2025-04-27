package com.kartik.LoginProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kartik.LoginProject.model.Feedback;

public interface FeedbackRepo extends JpaRepository<Feedback, Integer> {

}
