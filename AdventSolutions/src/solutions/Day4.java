package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import utils.DataHandling;

public class Day4 {

	private static String inputLine;
	private static String[] requiredValues = {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"};
	private static String[] eyeColorValues = {"amb", "blu", "brn", "gry", "grn", "hzl", "oth"};
	
	public static boolean validatePassportFields(ArrayList<String> passPortFields) {
		System.out.println();
		for(String requiredField : requiredValues) {
			if(!passPortFields.contains(requiredField)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean validatePassportData(ArrayList<String> passPortFields) {
		System.out.println(passPortFields);
		for(String requiredField : passPortFields) {
			String[] data = requiredField.split(":");
			System.out.println(requiredField);
			if(!isFieldValid(data[0], data[1])) {
				return false;
			}
		}
		return true;
	}
	
	
	public static boolean isFieldValid(String field, String value) {
		switch(field) {
			case "byr":
				return (Integer.parseInt(value) >=1920 && Integer.parseInt(value) <= 2002);
			case "iyr":
				return(Integer.parseInt(value) >= 2010 && Integer.parseInt(value) <= 2020);
			case "eyr":
				return(Integer.parseInt(value) >= 2020 && Integer.parseInt(value) <= 2030);	
			case "hgt":
				if(value.substring(value.length()-2).equals("cm")) {
					int height = Integer.parseInt(value.substring(0, value.length()-2));
					return (height <= 193 && height >= 150 );
					}else if (value.substring(value.length()-2).equals("in")) {
						int height = Integer.parseInt(value.substring(0, value.length()-2));
						return (height <= 76 && height >= 59 );
					}
				else {
						return false;
					}
			case "hcl":
				return(value.charAt(0)== '#' && value.length() == 7);
			case "ecl":
				return Arrays.stream(eyeColorValues).anyMatch(value::equals);
			case "pid":
				return(DataHandling.isNumeric(value) && value.length() == 9);
		default: 
			return true;
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		URL inputURL = Day1.class.getClassLoader().getResource("inputFiles/inputDay4.txt");
		BufferedReader inFile = new BufferedReader(
				new InputStreamReader(inputURL.openStream()));

		ArrayList<String> passPortFields = new ArrayList<>();
		ArrayList<String> fullPassportInfo = new ArrayList<>();
		int validPassports = 0;
		int fullValidPassports = 0;
        while ((inputLine = inFile.readLine()) != null) {
        	if(!inputLine.equals("")) {
        		String[] passPortInfo = inputLine.split(" ");
        		for (String info : passPortInfo) {
        			String[] information = info.split(":");
        			passPortFields.add(information[0]);
        			fullPassportInfo.add(info);
        		}
        	}else {
        		if(validatePassportFields(passPortFields)) {
        			validPassports++;
        			if(validatePassportData(fullPassportInfo)) {
        				fullValidPassports++;
        			}
        	}
        		passPortFields = new ArrayList<>();
        		fullPassportInfo = new ArrayList<>();
        	}
        }
        if(validatePassportFields(passPortFields)) {
			validPassports++;
			if(validatePassportData(fullPassportInfo)) {
				fullValidPassports++;
			}
		}
        System.out.println("The amount of simple valid passports are: "+validPassports);
        System.out.println("The amount of full valid passports are: "+ fullValidPassports);
        inFile.close();
	}

}
