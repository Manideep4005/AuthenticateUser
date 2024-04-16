package autorizeUser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import autorizeUser.service.UserService;
import net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy.Listener.ErrorEscalating;

@Controller
public class UserMailController {

	@Autowired
	private UserService userService;

	@GetMapping("/userActivateMail")
	public String sendUserActivationMail(RedirectAttributes ra) {

		ra.addFlashAttribute("userCreated", "A mail was sent please verify your account to login");
		return "redirect:/userlogin";

	}

	
	@GetMapping("account-activate")
	public String activateUser(@RequestParam("token") String myHash, @RequestParam("id") Integer userId, RedirectAttributes ra) {
		
		myHash = myHash.replace(' ', '+');
		
		System.out.println("TOEKN: " + myHash + "\nuserId: " + userId + "\n");
		
		Integer result = userService.activateUserAccount(userId, myHash);
		
		if (result != null && result > 0) {
			
			ra.addFlashAttribute("userAccountActivateMessage", "Account Activated");
			return "redirect:/userlogin";
		} else if ( result == 0) {
			ra.addFlashAttribute("userAccountActivateMessage", "Invalid token");
			return "redirect:/userlogin";
		}
		else {
			
			ra.addFlashAttribute("userAccountActivateMessage", "Account Activation failed");
			return "redirect:/userlogin";
		}
		
	}
	
	
	
	@GetMapping("/user-reset-password")
	public String getForm(Model theModel) {
		
		String error = (String) theModel.asMap().get("userPasswordMailError");
		
		if (error != null) {
			theModel.addAttribute("userPasswordMailError", error);
			return "user-password-mail-form";
		}
		
		return "user-password-mail-form";
	}
	
}
