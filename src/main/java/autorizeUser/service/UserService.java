package autorizeUser.service;

import java.util.List;

import autorizeUser.entity.UserAuth;

public interface UserService {
	
	public void saveUser(UserAuth theUser);
	
	public List<UserAuth> loginValidate(String email, String password);
	
	public Integer activateUserAccount(Integer userId, String myHashToken);
	
	public void activateUserAccount(String mail);

	public boolean userSendForgotPasswordOTP(String email, Integer otp);

	public void userUpdatePassword(String email, String password);

	
}
