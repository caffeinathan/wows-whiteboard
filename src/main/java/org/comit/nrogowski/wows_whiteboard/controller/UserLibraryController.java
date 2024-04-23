package org.comit.nrogowski.wows_whiteboard.controller;

import org.comit.nrogowski.wows_whiteboard.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLibraryController {
	@GetMapping("/library")
	public String userLibrary(Model model) {
//		new {
//
//		} userLibrary;
//		model.addAttribute(model)
		
		return "userlibrary";
	}
	
	@GetMapping("/librariless")
	public String librarilessTempPage( ) {
		// if we forget to disable this binding in the future,
		// we can still rig this to not allow privileged access on deployment
		return (Constants.ENABLE_DEBUG) ? "librariless" : "userlibrary";
	}
}