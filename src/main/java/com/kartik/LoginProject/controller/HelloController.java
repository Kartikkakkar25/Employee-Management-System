package com.kartik.LoginProject.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kartik.LoginProject.model.UserPrincipal;

@Controller
public class HelloController {

//	@GetMapping("/")
//	//Using Model here because we are in the same request-response cycle and need to pass data to ThymLeaf template
//	public String direct(Model model) {
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String username = ((UserPrincipal) principal).getUsername();
//		model.addAttribute("username", username);
//		return "index";
//	}
	 @GetMapping("/")
	 public ModelAndView homeRedirectBlank() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        
	        System.out.println("User Authorities: " + authentication.getAuthorities());
	        if (authentication.getAuthorities().stream()
	                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
	            return new ModelAndView("redirect:/index"); // Redirect to admin's home
	        } else {
	            return new ModelAndView("redirect:/indexUser"); // Redirect to user's home
	        }
	    }
	 @GetMapping("/home")
	    public ModelAndView homeRedirect() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        
	        System.out.println("User Authorities: " + authentication.getAuthorities());
	        if (authentication.getAuthorities().stream()
	                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
	            return new ModelAndView("redirect:/index"); // Redirect to admin's home
	        } else {
	            return new ModelAndView("redirect:/indexUser"); // Redirect to user's home
	        }
	    }
	 
	 @GetMapping("/indexUser")
	 public String userHome(Model model) {
	     Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	     String username = ((UserPrincipal) principal).getUsername();
	     model.addAttribute("username", username);
	     return "indexUser"; // This should match the HTML file name
	 }
	 @GetMapping("/index")
	 public String adminHome(Model model) {
	     Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	     String username = ((UserPrincipal) principal).getUsername();
	     model.addAttribute("username", username);
	     return "index"; // This should match the HTML file name
	 }
	
	
//	@PreAuthorize("hasRole('USER')")
//	@GetMapping("/home")
//	//Using Model here because we are in the same request-response cycle and need to pass data to ThymLeaf template
//	public String helloUser(Model model) {
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String username = ((UserPrincipal) principal).getUsername();
//		model.addAttribute("username", username);
//		return "indexUser";
//	}
//	@PreAuthorize("hasRole('USER')")
//	@GetMapping("/")
//	//Using Model here because we are in the same request-response cycle and need to pass data to ThymLeaf template
//	public String directUser(Model model) {
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String username = ((UserPrincipal) principal).getUsername();
//		model.addAttribute("username", username);
//		return "indexUser";
//	}
}
