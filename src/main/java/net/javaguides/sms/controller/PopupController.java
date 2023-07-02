package net.javaguides.sms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class PopupController {

	@GetMapping("/button-click")
    @ResponseBody
    public String handleButtonClick() {
        // Process your button click logic here
		System.out.println("hello Pranali dont give up");
        // Return the success message as a response
        return "Success";
    }
	
}
