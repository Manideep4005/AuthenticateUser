package autorizeUser.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import autorizeUser.entity.UserAuth;
import autorizeUser.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String getHome() {

		return "redirect:/userlogin";
	}

	
	
	// User Login and redirect methods
	@GetMapping("/userlogin")
	public String getUserLogin(Model theModel) {

		/* new User message */
		String userCreateMessage = (String) theModel.asMap().get("userCreated");

		if (userCreateMessage != null) {
			theModel.addAttribute("userCreated", "A mail was sent please verify your account ");
			return "user-login";
		}

		/* user logout message */
		String userLogoutMsg = (String) theModel.asMap().get("userLogoutMessage");

		if (userLogoutMsg != null) {
			theModel.addAttribute("userLogoutMessage", userLogoutMsg);
			return "user-login";
		}

		/* User account verify */

		String verifyUser = (String) theModel.asMap().get("userVerfiyEmail");

		if (verifyUser != null) {
			theModel.addAttribute("userVerfiyEmail", "A mail was sent please verify your account");
			return "user-login";
		}

		/* User Login Failed */
		String loginFail = (String) theModel.asMap().get("userLoginFailed");

		if (loginFail != null) {
			theModel.addAttribute(loginFail);
			return "user-login";
		}
		
		
		/* user account activated message */
		String userAccountActiveMsg = (String) theModel.asMap().get("userAccountActivateMessage");
		
		if (userAccountActiveMsg != null) {
			theModel.addAttribute("accountActivateMsg", userAccountActiveMsg);
			return "user-login";
		}
		
		
		/* password reset message */
		String passwordReset = (String) theModel.asMap().get("passwordChangeSuccess");
		
		if (passwordReset != null) {
			theModel.addAttribute("passwordChangeSuccess", passwordReset);
			return "user-login";
		}
		
		return "user-login";
	}
	
	
	

	// user register form and redirect method
	@GetMapping("/register")
	public String getRegister(Model theModel) {

		UserAuth user_auth = new UserAuth();

		theModel.addAttribute("user_auth", user_auth);

		String RegiserErrorMsg = (String) theModel.asMap().get("userRegisterError");

		if (RegiserErrorMsg != null) {
			theModel.addAttribute("userRegisterError", RegiserErrorMsg);
			theModel.addAttribute("user_auth", user_auth);

			return "user-register";
		}

		return "user-register";
	}

	
	
	
	// user register
	@PostMapping("/register")
	public String saveUser(@ModelAttribute("user_auth") UserAuth theUser, HttpServletRequest request,
			RedirectAttributes ra) {

		try {
			userService.saveUser(theUser);
			
			return "redirect:/userActivateMail";

		} catch (ConstraintViolationException e) {
			System.out.println("\nFROM USER CONTROLLER : " + e + "\n");
			ra.addFlashAttribute("userRegisterError", "Email already in use");
			return "redirect:/register";
		}

	}

	
	
	// User login process
	@PostMapping("/loginuser")
	public String loginProcess(@RequestParam("username") String username, @RequestParam("password") String password,
			Model theModel, HttpServletRequest request, RedirectAttributes ra) {

		String href = "";

		try {

			HttpSession session = request.getSession();

			UserAuth theUser = new UserAuth();

			List<UserAuth> list = userService.loginValidate(username, password);

			System.out.println("FROM USERCONTROLLER LIST SIZE : " + list.size());
			if (list.size() == 0 || list == null) {
				System.out.println();
				System.out.println("\nuser Login failed from method in user controller\n");
				ra.addFlashAttribute("userLoginFailed", "Invalid username/password");
				href = "redirect:/userlogin";
			
			}

			Iterator<UserAuth> itr = list.iterator();

			Integer userId = null;
			String FirstName = "";
			String lastName = "";
			Integer userActive = null;
			Integer acitve = 1;
			String userEmail = "";

			if (list.size() != 0) {
				
				while (itr.hasNext()) {
					theUser = itr.next();
					userId = theUser.getUserId();
					FirstName = theUser.getFirstName();
					lastName = theUser.getLastName();
					userActive = theUser.getActive();
					userEmail = theUser.getEmail();
				}
			}

			if (userActive.equals(acitve)) {
				
				System.out.println("\nLogged In User Details\n----------------------\nUser Id:" + userId
						+ "\nUser Name: " + FirstName + " " + lastName + "\n----------------------\n\n");

				session.setAttribute("currentUserId", userId);
				session.setAttribute("currentUserName", FirstName + " " + lastName);

				href = "redirect:/Home";
				
			} else if (!userActive.equals(acitve)) {
		
				session.setAttribute("userEmailVerify", username);
				
				ra.addFlashAttribute("userVerfiyEmail", "A mail was sent please activate your account");

				userService.activateUserAccount(userEmail);
				
				System.out.println(
						"\nFROM USER CONTROLLER >>> User login declined user haven't verified their account\nUser Login Failed\n");
				href = "redirect:/userlogin";
			
			}

			return href;
			
		} catch (NullPointerException e) {
			
			System.out.println("\nuser Login failed from Exception user controller\n");
			ra.addFlashAttribute("userLoginFailed", "Invalid username/password");
			return "redirect:/userlogin";
		
		}
	}

	
	
	// user logout controller
	@GetMapping("/logout")
	public String getString(RedirectAttributes ra, HttpServletRequest request) {

		HttpSession session = request.getSession();

		System.out.println(
				"\nLogged out User Details\n----------------------\nUser Id:" + session.getAttribute("currentUserId")
						+ "\nUser Name: " + session.getAttribute("currentUserName") + "\n----------------------\n\n");

		session.removeAttribute("currentUserId");
		session.removeAttribute("currentUserName");

		session.invalidate();

		ra.addFlashAttribute("userLogoutMessage", "Logout successfully.");

		return "redirect:/userlogin";
	}
	
	
	@GetMapping("/user-password-mail-form")
	public String getPasswordForgotMailForm() {
		return "redirect:/user-reset-password";
	}
	
	
	@PostMapping("/user-password-send-mail")
	public String getUserPasswordSendMail(Model theModel, HttpServletRequest request,RedirectAttributes ra, @RequestParam("userEmail") String email) {
		
		String href = "";
		
		HttpSession session = request.getSession();
		
		Random random = new Random();
		Integer otp = random.nextInt(999999 - 1 + 1) + 1;
		
		boolean status = userService.userSendForgotPasswordOTP(email, otp);
		
		if (status) {
			session.setAttribute("userPasswordMailOtp", otp);
			session.setAttribute("userPasswordMailEmail", email);
			ra.addFlashAttribute("userMailSentOtp", "An email with otp was sent to " + email + " if there is an account exists.");
			href = "redirect:/user-otp-validate";
		} else {
			ra.addFlashAttribute("userPasswordMailError", "An email with otp was sent to " + email + " if there is an account exists.");
			href = "redirect:/user-otp-validate";
		}
		
		
		return href;
	}
	
	
	@GetMapping("/user-otp-validate")
	public String getoptform(HttpSession session, Model theModel) {
		Integer sentOtp = (Integer) session.getAttribute("userPasswordMailOtp");
		String userEmail = (String)session.getAttribute("userPasswordMailEmail");
		
		String msg = (String) theModel.asMap().get("userMailSentOtp");
		
		
		if (msg != null) {
			session.setAttribute("userPasswordMailOtp", sentOtp);
			session.setAttribute("userPasswordMailEmail", userEmail);
			
			theModel.addAttribute("userMailSentOtp", msg);
			
			return "user-password-otp-form";
		}
		
		String otpError = (String)theModel.asMap().get("otpError");
		
		if (otpError != null) {
			
			theModel.addAttribute("otpError", otpError);
			return "user-password-otp-form";
		}
		
		return "user-password-otp-form";
	}
	
	
	@PostMapping("/user-otp-validate")
	public String getOtpValidate(HttpSession session, RedirectAttributes ra, @RequestParam("userOtp") Integer otp) {
		Integer sentOtp = (Integer) session.getAttribute("userPasswordMailOtp");
		String userEmail = (String)session.getAttribute("userPasswordMailEmail");
		
		String href="";
		
		System.out.println("\nFROM USER CONTROLLER");
		System.out.println("user Entered OTP : "+otp);
		System.out.println("Sent otp : " + sentOtp);
		System.out.println("");
		
		if (otp.equals(sentOtp)) {
			
			
			session.setAttribute("userPasswordMailEmail", userEmail);
			href = "redirect:/user-password-change";
		} else {
			ra.addFlashAttribute("otpError", "Invalid Otp");
			href = "redirect:/user-otp-validate";
		}
		
		return href;
		
	}
	
	@GetMapping("user-password-change")
	public String getPasswordChangeForm() {
		return "user-password-change-form";
	}
	
	
	@PostMapping("/user-password-change")
	public String userPasswordChange(RedirectAttributes ra, @RequestParam("userPassword") String password, HttpSession session) {
		
		String email = (String) session.getAttribute("userPasswordMailEmail");
		
		userService.userUpdatePassword(email, password);
		
		
		
		ra.addFlashAttribute("passwordChangeSuccess", "password reset success");
		
		return "redirect:/userlogin";
	}

}
