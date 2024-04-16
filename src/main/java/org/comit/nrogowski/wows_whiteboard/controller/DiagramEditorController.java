package org.comit.nrogowski.wows_whiteboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DiagramEditorController {
	
	@GetMapping("/diagram/{id}")
	ModelAndView startEditor() {
		return null;
	}
	
	
}
