package com.kartik.LoginProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kartik.LoginProject.model.Feedback;
import com.kartik.LoginProject.model.UserPrincipal;
import com.kartik.LoginProject.service.FeedbackService;

@Controller
public class feedbackController {
	
	
	@Autowired
	private FeedbackService feedbackService;

	@GetMapping("/feedback")
    public String feedback(Model model) {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserPrincipal) principal).getUsername();
		model.addAttribute("username",username);
		model.addAttribute("feedback_type", List.of("Bug Report", "Feature Request", "General Feedback"));
		model.addAttribute("feedback_rating", List.of(1, 2, 3, 4, 5));
		model.addAttribute("feedback", new Feedback());
        return "feedback";
    }
	
	
    @PostMapping("/feedback")
    public String submitFeedback(@ModelAttribute Feedback feedback, RedirectAttributes redirectAttributes) {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserPrincipal) principal).getUsername();
		int empid = ((UserPrincipal) principal).getEmp_id();
        feedback.setUsername(username);
        feedback.setEmp_id(empid);
        feedbackService.saveFeedback(feedback);
        redirectAttributes.addFlashAttribute("successMessage", "Feedback submitted successfully!");
        return "redirect:/feedback?success";
    }
}
