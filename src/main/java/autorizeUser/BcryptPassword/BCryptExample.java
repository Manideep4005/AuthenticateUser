package autorizeUser.BcryptPassword;

public class BCryptExample {

	public static void main(String[] args) {
		String password = "Manideep Nakka";
		String generateBCryptPass = BCrypt.hashpw(password, BCrypt.gensalt(5));
		System.out.println("GENERATED HASH: \n" + generateBCryptPass);

		boolean match = BCrypt.checkpw(password, generateBCryptPass);
		System.out.println(match);

		match = BCrypt.checkpw("manideep nakka", generateBCryptPass);
		System.out.println(match);

	}
}
