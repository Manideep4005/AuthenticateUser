package autorizeUser.service;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autorizeUser.dao.UserDao;
import autorizeUser.entity.UserAuth;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public void saveUser(UserAuth theUser) {
		userDao.saveUser(theUser);
	}

	@Override
	@Transactional
	public List<UserAuth> loginValidate(String email, String password) {
		return userDao.loginValidate(email, password);
	}

	@Override
	@Transactional
	public Integer activateUserAccount(Integer userId, String myHashToken) {
		
		return userDao.activateUserAccount(userId, myHashToken);
		
	}

	@Override
	@Transactional
	public void activateUserAccount(String mail) {
		// TODO Auto-generated method stub
		userDao.activateUserAccount(mail);
	}

	@Override
	@Transactional
	public boolean userSendForgotPasswordOTP(String email, Integer otp) {
		// TODO Auto-generated method stub
		return userDao.userSendForgotPasswordOTP(email, otp);
	}

	@Override
	@Transactional
	public void userUpdatePassword(String email, String password) {
		// TODO Auto-generated method stub
		userDao.userUpdatePassword(email, password);
	}

	

	
}
