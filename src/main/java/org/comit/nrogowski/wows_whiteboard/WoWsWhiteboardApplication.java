package org.comit.nrogowski.wows_whiteboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WoWsWhiteboardApplication {
	
	public static final boolean ENABLE_DEBUG = true;

	public static void main(String[] args) {
		SpringApplication.run(WoWsWhiteboardApplication.class, args);
	}

}
