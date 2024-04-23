package org.comit.nrogowski.wows_whiteboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
	
	// prototyping page, not for publication
	@GetMapping("/hello")
	public String helloWorld() {
		return "index";
	}
	
	@GetMapping("/")
	public String landingPage() {
		return "landing";
	}
	
	@GetMapping("/checklogin")
	public String checkLogin(
		@RequestParam String access_token,
		@RequestParam String expires_at,
		@RequestParam String account_id,
		@RequestParam String nickname) {
		
		System.out.printf("%s (%s) is logged in with expiry %s, access token %n%s%n",
				nickname, account_id, expires_at, access_token);
		
		return "checklogin";
	}
}
