package autorizeUser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	// Home controller
	@GetMapping("/Home")
	public String userAuthHome() {
		return "Home";
	}
}
