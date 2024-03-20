package org.comit.nrogowski.wows_whiteboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping("/")
	public String helloWorld() {
		return "index";
	}
	
}
