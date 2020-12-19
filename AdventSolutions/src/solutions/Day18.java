package solutions;

import java.io.IOException;
import java.util.ArrayList;

import utils.DataHandling;

public class Day18 {

	public static int stringIndex = 0;
	
	public static long parseData(String information) {
		stringIndex = 0;
		long value = 0;
		information = information.replace(" ", "");
		while(stringIndex < information.length()) {
			value = parseData(information, value);
		}
		return value;
	}
	
	public static long parseData(String information, long value) {
		long parseValue = value;
		
		if(information.charAt(stringIndex) == '(') {
					stringIndex++;
					parseValue = parseData(information ,0);
					while(information.charAt(stringIndex)!= ')') {
					
					parseValue = parseData(information ,parseValue);
					
					}
					stringIndex++;
				}
		else if(information.charAt(stringIndex) == ')') {
					stringIndex++;
					return parseValue;
				}
		else if(information.charAt(stringIndex)== '+') {
				stringIndex++;
				return parseValue += parseData(information, parseValue);
			}
		else if(information.charAt(stringIndex)== '-') {
				stringIndex++;
				return parseValue -= parseData(information, parseValue);
			}
		else if(information.charAt(stringIndex)== '*') {
				stringIndex++;
				return parseValue *= parseData(information, parseValue);
			}
		else {
				parseValue = Long.parseLong(String.valueOf(information.charAt(stringIndex)));
				stringIndex++;
		}
		return parseValue;
	}
	
	
	public static String parseDataTwo(String information) {
		information = information.replace(" ", "");
		String result = information;
		int firstIndex = information.indexOf('(');
		int firstEndIndex = information.indexOf(')');
		int buffert = 0;
		if(firstEndIndex != -1 ) {
			String subInformation = information.substring(firstIndex +1, firstEndIndex);

			while(subInformation.contains("(")){
				firstIndex = subInformation.indexOf('(');
				buffert += firstIndex+1;
				firstEndIndex = information.substring(firstIndex).indexOf(')');
				subInformation = information.substring(buffert+1, firstEndIndex+firstIndex);
			}
				if (buffert == 0) {
					result = information.substring(0, firstIndex) +parseDataTwo(subInformation) + information.substring(firstEndIndex + 1);
				}
				else if(buffert != -1 && buffert != 0) {
					result = information.substring(0, buffert) +parseDataTwo(subInformation) + information.substring(firstEndIndex + firstIndex +1);
				} else {
					
					result = parseDataTwo(subInformation) + information.substring(firstEndIndex+1 + firstIndex);
				}
			}
		else {
			long eval = 0;
			String value1 = "";
			String value2 = "";
			int plusIndex = information.indexOf('+');
			int multIndex = information.indexOf('*');
			
			if(plusIndex >-1) {
//				
				for ( int i = plusIndex-1; i > -1; i--) {
					if(Character.isDigit(information.charAt(i))) {
						value1 = information.charAt(i) + value1;
					}
					else {
						break;
					}
				}
				for ( int i = plusIndex+1; i < information.length(); i++) {
					if(Character.isDigit(information.charAt(i))) {
						value2 +=  information.charAt(i) ;
					}
					else {
						break;
					}
				}
				eval = Long.parseLong(value1) + Long.parseLong(value2);
				result = information.substring(0,plusIndex-value1.length()) + String.valueOf(eval) + information.substring(plusIndex+value2.length()+1);
			}
				else if(multIndex > -1) {
					for ( int i = multIndex-1; i > -1; i--) {
						if(Character.isDigit(information.charAt(i))) {
							value1 = information.charAt(i) + value1;
						}
						else {
							break;
						}
					}
					for ( int i = multIndex+1; i < information.length(); i++) {
						if(Character.isDigit(information.charAt(i))) {
							value2 +=  information.charAt(i) ;
						}
						else {
							break;
						}
					}
					eval = Long.parseLong(value1) * Long.parseLong(value2);
					result = information.substring(0,multIndex-value1.length()) + String.valueOf(eval) + information.substring(multIndex+value2.length()+1);
		}
				else {
					result = information;
				}
		}
		try {
			long parseValue =Long.parseLong(result);
		} catch (Exception e) {
			result = parseDataTwo(result);
		}
		return result;
	}
	
	
	public static void main(String[] args) throws IOException {
		 ArrayList<String> lines = DataHandling.readStringsBySeparatorRaw("inputFiles/inputDay18.txt");
		 long sum = 0;
		 for(String line : lines) {
			 sum+= parseData(line);
		 }
		 
		 long sum2 = 0;
		 for(String line :lines) {
			 sum2+= Long.parseLong(parseDataTwo(line));
		 }
		 System.out.println("The answer for part A is : "+sum);
		 System.out.println("The answer for part B is : "+sum2);
		 
	}
}
