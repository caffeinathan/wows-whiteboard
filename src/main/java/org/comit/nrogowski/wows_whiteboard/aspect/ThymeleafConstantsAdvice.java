package org.comit.nrogowski.wows_whiteboard.aspect;

import java.lang.reflect.Field;

import org.comit.nrogowski.wows_whiteboard.Constants;
import org.comit.nrogowski.wows_whiteboard.exceptions.WowsWhiteboardException;
import org.comit.nrogowski.wows_whiteboard.util.ThymeleafConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ThymeleafConstantsAdvice {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ModelAttribute
	public void addThymeleafConstants(Model model) {
		Field[] fields = Constants.class.getDeclaredFields();
		for (Field field: fields) {
			if (field.isAnnotationPresent(ThymeleafConstant.class) ) {
				try {
					String fieldName = field.getName();
					String constantValue = (String) field.get(null);
					model.addAttribute(fieldName, constantValue);
				} catch (IllegalAccessException e) {
					logger.debug("Error in custom constant handling code", e);
					if (Constants.ENABLE_DEBUG) throw new WowsWhiteboardException(e);
				}
			}
		}
	}
}
