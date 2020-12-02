package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Day2 {

	private static String inputLine;
	
	public static boolean validatePassword(String passwordInfo) {
		String[] words = passwordInfo.split(" ");
		String[] interval = words[0].split("-");
		int min = Integer.parseInt(interval[0]);
		int max = Integer.parseInt(interval[1]);
		char character = words[1].charAt(0);;
		String password = words[2];
		long count = password.chars().filter(ch -> ch == character).count();
		if(count <= max && count >= min) {
			return true;
		}
		
		return false;
		
	}
	
	public static boolean validatePasswordRoutine2(String passwordInfo) {
		String[] words = passwordInfo.split(" ");
		String[] interval = words[0].split("-");
		int[] positions = {Integer.parseInt(interval[0])-1, Integer.parseInt(interval[1])-1};
		char character = words[1].charAt(0);;
		String password = words[2];
		int count = 0;
		for(int position : positions) {
			if(password.charAt(position) == character) {
				count++;
			}
		}
		if(count == 1 ) {
			return true;
		}
		
		return false;
		
	}
	
	public static void main(String[] args) throws IOException {
		URL inputURL = Day1.class.getClassLoader().getResource("inputFiles/inputDay2.txt");
		BufferedReader inFile = new BufferedReader(
				new InputStreamReader(inputURL.openStream()));

		int validatedPasswords = 0;
		int validatedPasswordsAlternative = 0;
        while ((inputLine = inFile.readLine()) != null) {
        	if(validatePassword(inputLine)) {
        		validatedPasswords++;
        	}
        	if(validatePasswordRoutine2(inputLine)){
        		validatedPasswordsAlternative++;
        	}
        	}
        System.out.println("The amount of valid passwords are: " + validatedPasswords);
        System.out.println("The amount of valid passwords for routine 2 are: " + validatedPasswordsAlternative);
        inFile.close();
	}
}
