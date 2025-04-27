package com.kartik.LoginProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kartik.LoginProject.dao.FeedbackRepo;
import com.kartik.LoginProject.model.Feedback;

@Service
public class FeedbackService {
	 @Autowired
	 private FeedbackRepo feedbackRepo;

    public void saveFeedback(Feedback feedback) {
	        feedbackRepo.save(feedback);
	    }
}
