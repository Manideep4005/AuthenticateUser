package autorizeUser.mailing;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class MailConfig {

	/* Method to get current system ip address */
	public static String ipAddress() throws UnknownHostException {
		InetAddress ip = InetAddress.getLocalHost();

		String address = ip.getHostAddress();

		return address;
	}

	/* setting up mail username and password */
	private final String mailUserName = "systems2hms";
	private final String mailPassword = "raci ycsi wean vezu";

	/* SMTP PROPERTIES */
	private Properties getProperties() {
		Properties properties = new Properties();

		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.host", "smtp.gmail.com");

		return properties;
	}

	/* Setting up Mail for user Activation */
	public boolean userActivateMail(String userEmail, String hash, String userName, Integer userId) {

		boolean flag = false;

		Session session = Session.getInstance(getProperties(), new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailUserName, mailPassword);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
			message.setFrom(new InternetAddress("systems2hms@gmail.com", "no-replay@User Authorize"));
			message.setSubject("Account Activation");
			message.setContent("<html lang=\"en\">\r\n"
					+ "  <head>\r\n"
					+ "    <meta charset=\"UTF-8\" />\r\n"
					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n"
					+ "\r\n"
					+ "    <style>\r\n"
					+ "      body {\r\n"
					+ "        font-size: 18px;\r\n"
					+ "      }\r\n"
					+ "\r\n"
					+ "      .container {\r\n"
					+ "        top: 50%;\r\n"
					+ "        left: 50%;\r\n"
					+ "        transform: translate(-50%, -50%);\r\n"
					+ "        position: absolute;\r\n"
					+ "      }\r\n"
					+ "\r\n"
					+ "      .note-mail {\r\n"
					+ "        font-size: 14px;\r\n"
					+ "        text-align: center;\r\n"
					+ "      } .link a{text-decoration:none;color:dodgerblue;}\r\n"
					+ "\r\n"
					+ "      hr {\r\n"
					+ "        border-color: gray;\r\n"
					+ "      }\r\n"
					+ "    </style>\r\n"
					+ "  </head>\r\n"
					+ "  <body>\r\n"
					+ "    <div class=\"container\">\r\n"
					+ "      <div class=\"main\">\r\n"
					+ "        <p>Dear "+userName+",</p>\r\n"
					+ "        <p>Please activate your account by clicking below link</p>\r\n"
					+ "        <p class=\"link\"><a href=\"http://"+ipAddress()+":8081/autorizeUser/account-activate?token="+hash+"&id="+userId+"\">Activate Account</a></p>\r\n"
					+ "      </div>\r\n"
					+ "      <div class=\"thanks\">\r\n"
					+ "        <p>Thanks & Regards,<br />Admin</p>\r\n"
					+ "      </div>\r\n"
					+ "      <hr />\r\n"
					+ "      <div class=\"note\">\r\n"
					+ "        <p class=\"note-replay\">\r\n"
					+ "          ***This is system generated email please don't replay.\r\n"
					+ "        </p>\r\n"
					+ "        <br />\r\n"
					+ "        <p class=\"note-mail\">This email was intended to "+userEmail+"</p>\r\n"
					+ "      </div>\r\n"
					+ "    </div>\r\n"
					+ "  </body>\r\n"
					+ "</html>", "text/html");
			Transport.send(message);

			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There is an error sending mail");
		}

		return flag;
	}
	
	/* sending otp to user for password change */
	
	public boolean userResetPasswordMail(String userEmail, Integer otp, String userName) {

		boolean flag = false;

		Session session = Session.getInstance(getProperties(), new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailUserName, mailPassword);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
			message.setFrom(new InternetAddress("systems2hms@gmail.com", "no-replay@User Authorize"));
			message.setSubject("Request for Password Reset");
			message.setContent("<html lang=\"en\">\r\n"
					+ "  <head>\r\n"
					+ "    <meta charset=\"UTF-8\" />\r\n"
					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n"
					+ "\r\n"
					+ "    <style>\r\n"
					+ "      body {\r\n"
					+ "        font-size: 18px;\r\n"
					+ "      }\r\n"
					+ "\r\n"
					+ "      .container {\r\n"
					+ "        top: 50%;\r\n"
					+ "        left: 50%;\r\n"
					+ "        transform: translate(-50%, -50%);\r\n"
					+ "        position: absolute;\r\n"
					+ "      }\r\n"
					+ "\r\n"
					+ "      .note-mail {\r\n"
					+ "        font-size: 14px;\r\n"
					+ "        text-align: center;\r\n"
					+ "      }\r\n"
					+ "\r\n"
					+ "      hr {\r\n"
					+ "        border-color: gray;\r\n"
					+ "      }\r\n"
					+ "    </style>\r\n"
					+ "  </head>\r\n"
					+ "  <body>\r\n"
					+ "    <div class=\"container\">\r\n"
					+ "      <div class=\"main\">\r\n"
					+ "        <p>Dear "+userName+",</p>\r\n"
					+ "        <p>You have requested for password reset</p>\r\n"
					+ "        <p class=\"link\">The OTP is : "+otp+"</p>\r\n"
					+ "      </div>\r\n"
					+ "      <div class=\"thanks\">\r\n"
					+ "        <p>Thanks & Regards,<br />Admin</p>\r\n"
					+ "      </div>\r\n"
					+ "      <hr />\r\n"
					+ "      <div class=\"note\">\r\n"
					+ "        <p class=\"note-replay\">\r\n"
					+ "          ***This is system generated email please don't replay.\r\n"
					+ "        </p>\r\n"
					+ "        <br />\r\n"
					+ "        <p class=\"note-mail\">This email was intended to "+userEmail+"</p>\r\n"
					+ "      </div>\r\n"
					+ "    </div>\r\n"
					+ "  </body>\r\n"
					+ "</html>", "text/html");
			Transport.send(message);

			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There is an error sending mail");
		}

		return flag;
	}
	
	public boolean userResetPasswordMailSuccess(String userEmail, Timestamp stamp, String userName) {

		boolean flag = false;

		Session session = Session.getInstance(getProperties(), new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailUserName, mailPassword);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
			message.setFrom(new InternetAddress("systems2hms@gmail.com", "no-replay@User Authorize"));
			message.setSubject("Password Reset successfully");
			message.setContent("<html lang=\"en\">\r\n"
					+ "  <head>\r\n"
					+ "    <meta charset=\"UTF-8\" />\r\n"
					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n"
					+ "\r\n"
					+ "    <style>\r\n"
					+ "      body {\r\n"
					+ "        font-size: 19px;\r\n"
					+ "      }\r\n"
					+ "\r\n"
					+ "      .container {\r\n"
					+ "        top: 50%;\r\n"
					+ "        left: 50%;\r\n"
					+ "        transform: translate(-50%, -50%);\r\n"
					+ "        position: absolute;\r\n"
					+ "      }\r\n"
					+ "\r\n"
					+ "      .note-mail {\r\n"
					+ "        font-size: 14px;\r\n"
					+ "        text-align: center;\r\n"
					+ "      }\r\n"
					+ "\r\n"
					+ "      hr {\r\n"
					+ "        border-color: gray;\r\n"
					+ "      }\r\n"
					+ "    </style>\r\n"
					+ "  </head>\r\n"
					+ "  <body>\r\n"
					+ "    <div class=\"container\">\r\n"
					+ "      <div class=\"main\">\r\n"
					+ "        <p>Dear "+userName+",</p>\r\n"
					+ "        <p>Password changed successfully on</p>\r\n"
					+ "        <p class=\"link\">"+stamp+" (IST)</p>"
							+ "<p class=\\\"link\\\">click here for <a href=\"http://"+ipAddress()+":8081/autorizeUser/\">Login</a></p>\r\n"
					+ "      </div>\r\n"
					+ "      <div class=\"thanks\">\r\n"
					+ "        <p>Thanks & Regards,<br />Admin</p>\r\n"
					+ "      </div>\r\n"
					+ "      <hr />\r\n"
					+ "      <div class=\"note\">\r\n"
					+ "        <p class=\"note-replay\">\r\n"
					+ "          ***This is system generated email please don't replay.\r\n"
					+ "        </p>\r\n"
					+ "        <br />\r\n"
					+ "        <p class=\"note-mail\">This email was intended to "+userEmail+"</p>\r\n"
					+ "      </div>\r\n"
					+ "    </div>\r\n"
					+ "  </body>\r\n"
					+ "</html>", "text/html");
			Transport.send(message);

			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There is an error sending mail");
		}

		return flag;
	}
	
}
