package org.comit.nrogowski.wows_whiteboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLibraryController {
	@GetMapping("/library_sample")
	public String userLibrary(Model model) {
//		new {
//
//		} userLibrary;
//		model.addAttribute(model)
		
		return "userlibrary";
	}
}

//class UlTestUser {
//	public String username;
//	public String region; 
//}
//
//class UlTestBook {
//	UlTestUser
//}