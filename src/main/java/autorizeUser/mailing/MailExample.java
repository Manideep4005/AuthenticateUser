package autorizeUser.mailing;

import java.sql.Timestamp;
import java.time.Instant;

public class MailExample {
	public static void main(String[] args) {
		MailConfig config = new MailConfig();
//		boolean send = config.userActivateMail("nmd.mani@gmail.com", "12929297851#----#++++-2okqe", "Manideep", 12000);
//		boolean send2 = config.userResetPasswordMail("nmd.mani@gmail.com", 12345, "Manideep");
		
		Timestamp stamp = Timestamp.from(Instant.now());
		
		boolean send3 = config.userResetPasswordMailSuccess("nmd.mani@gmail.com", stamp, "Manideep");
		
		if (send3) {
			System.out.println("Mail was sent");
		} else {
			System.out.println("Mail not sent");
		}
		
	} 
}
