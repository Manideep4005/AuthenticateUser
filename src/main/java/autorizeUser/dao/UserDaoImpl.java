package autorizeUser.dao;

import java.lang.Thread.State;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import autorizeUser.BcryptPassword.BCrypt;
import autorizeUser.entity.UserAuth;
import autorizeUser.mailing.MailConfig;

@Repository
public class UserDaoImpl implements UserDao {

	private final SecureRandom secureRandom = new SecureRandom();
	private final Base64.Encoder base64Encoder = Base64.getEncoder();

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveUser(UserAuth theUser) {

		/* creating a hash token to verify user account */
		byte[] hashBytes = new byte[64];
		secureRandom.nextBytes(hashBytes);
		String myhash = base64Encoder.encodeToString(hashBytes);

		System.out.println("\nFROM USER DAO VERIFY TOKEN HASH : " + myhash + "\n");

		Session currentSession = sessionFactory.getCurrentSession();

		String userEnteredPass = theUser.getPassword(); // Getting the user enter password

		String generatedhash = BCrypt.hashpw(userEnteredPass, BCrypt.gensalt(10)); // converting password to hash

		theUser.setPassword(generatedhash); // registering manually the password
		theUser.setActive(0); // setting the user active to Not Active -> 0 \\ 1 -> Active
		theUser.setVerifyToken(myhash); // setting the verify token.

		currentSession.saveOrUpdate(theUser);

		Session currentSession2 = sessionFactory.getCurrentSession();
		Integer userId = theUser.getUserId();

		UserAuth theUser2 = (UserAuth) currentSession2.get(UserAuth.class, userId);

		MailConfig sendMail = new MailConfig();

		boolean mailSent = sendMail.userActivateMail(theUser2.getEmail(), myhash, theUser2.getFirstName(),
				theUser2.getUserId());

		if (mailSent) {
			System.out.println("\nFROM USER SAVE DAO EMAIL SENT SUCCESS\n" + "User Name: " + theUser2.getFirstName()
					+ "\nUser EMail: " + theUser2.getEmail() + "\nUser id: " + theUser2.getUserId() + "\n");

		} else {
			System.out.println("There is an error sending mail");
		}

	}

	@Override
	public List<UserAuth> loginValidate(String email, String password) {

		UserAuth theUser = new UserAuth();

		List<UserAuth> getUserList = null;

		Session currentSession = sessionFactory.getCurrentSession();

		String hasedPassword = "";

		boolean checkPass = false;
		try {
			Query<UserAuth> query = currentSession.createQuery("from UserAuth where email =:e", UserAuth.class);
			query.setParameter("e", email);

			getUserList = query.getResultList();

			if (getUserList.size() != 0 || getUserList.size() > 0) {
				Iterator<UserAuth> itr = getUserList.iterator();

				while (itr.hasNext()) {
					theUser = itr.next();
					hasedPassword = theUser.getPassword();
					System.out.println("\nFROM DAO IMPL USER ACTIVE >>>> " + (theUser.getActive() == 1 ? "YES\n" : "NO\n"));

				}

				checkPass = BCrypt.checkpw(password, hasedPassword);

			}

			if (checkPass) {
				return getUserList;
			} else
				return null;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public Integer activateUserAccount(Integer userId, String myHashToken) {

		Session currentSession = sessionFactory.getCurrentSession();

		Integer result = 0;

		Query theQuery = currentSession.createQuery(
				"update UserAuth set active =:active, verifyToken =:tokenupdate where verifyToken =:token and userId =:userid");
		theQuery.setParameter("active", 1);
		theQuery.setParameter("tokenupdate", "verified");
		theQuery.setParameter("token", myHashToken);
		theQuery.setParameter("userid", userId);

		result = theQuery.executeUpdate();

		return result;

	}

	@Override
	public void activateUserAccount(String mail) {

		Session currentSession = sessionFactory.getCurrentSession();

		List<UserAuth> getUserList = null;

		UserAuth theUser = new UserAuth();

		try {

			String email = "";
			Integer userId = 0;
			String userFname = "";
			String hash = "";

			Query<UserAuth> query = currentSession.createQuery("from UserAuth where email =:e", UserAuth.class);
			query.setParameter("e", mail);

			getUserList = query.getResultList();

			if (getUserList.size() != 0 || getUserList.size() > 0) {
				Iterator<UserAuth> itr = getUserList.iterator();

				while (itr.hasNext()) {
					theUser = itr.next();
					email = theUser.getEmail();
					userId = theUser.getUserId();
					userFname = theUser.getFirstName();
					hash = theUser.getVerifyToken();
				}
			}

			MailConfig sentMail = new MailConfig();

			boolean b = sentMail.userActivateMail(email, hash, userFname, userId);

			if (b) {
				System.out.println("\nFROM USER DAO IMPL : \nA MAIL WAS SENT FOR USER ACCOUNT VERIFY");
			} else {
				System.out.println("\nFROM USER DAO IMPL : \nA MAIL WAS NOT SENT");
			}

		} catch (Exception e) {
			System.out.println("\nFROM USER DAO IMPL : \n" + e);
		}

	}

	@Override
	public boolean userSendForgotPasswordOTP(String email, Integer otp) {

		boolean status = false;

		Session currentSession = sessionFactory.getCurrentSession();

		List<UserAuth> getUserList = null;

		UserAuth theUser = new UserAuth();

		try {

			Integer userId = 0;
			String userFname = "";
			String userEmail = "";

			Query<UserAuth> query = currentSession.createQuery("from UserAuth where email =:e", UserAuth.class);
			query.setParameter("e", email);

			getUserList = query.getResultList();

			if (getUserList.size() != 0 || getUserList.size() > 0) {
				Iterator<UserAuth> itr = getUserList.iterator();

				while (itr.hasNext()) {
					theUser = itr.next();
					userId = theUser.getUserId();
					userFname = theUser.getFirstName();
					userEmail = theUser.getEmail();
				}
			}

			MailConfig config = new MailConfig();

			boolean b = config.userResetPasswordMail(userEmail, otp, userFname);

			if (b) {
				status = true;
			} else {
				status = false;
			}
			
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

	
	/* Updating user Password */
	@Override
	public void userUpdatePassword(String email, String password) {

		Session currentSession = sessionFactory.getCurrentSession();
		UserAuth theUser = new UserAuth();
		List<UserAuth> userList = new ArrayList<>();
		
		try {
			
			Integer userId = 0;
			String userFname = "";
			String userEmail = "";
			
			
			Query<UserAuth> query = currentSession.createQuery("from UserAuth where email =:e", UserAuth.class);
			query.setParameter("e", email);

			userList = query.getResultList();

			if (userList.size() != 0 || userList.size() > 0) {
				Iterator<UserAuth> itr = userList.iterator();

				while (itr.hasNext()) {
					theUser = itr.next();
					userId = theUser.getUserId();
					userFname = theUser.getFirstName();
					userEmail = theUser.getEmail();
				}
			}
			
			UserAuth theUser2 = (UserAuth) currentSession.get(UserAuth.class, userId);
			
			String HashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
			
			theUser2.setPassword(HashedPassword);
			
			currentSession.update(theUser2);
			
			Timestamp stamp = Timestamp.from(Instant.now());
			
			MailConfig config = new MailConfig();
			boolean b = config.userResetPasswordMailSuccess(userEmail, stamp, userFname);
			
			if (b) {
				System.out.println("Success mail sent");
			} else {
				System.out.println("Success mail not sent");
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
