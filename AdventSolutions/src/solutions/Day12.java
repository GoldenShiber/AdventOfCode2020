package solutions;

import java.io.IOException;
import java.util.ArrayList;


import utils.DataHandling;

public class Day12 {

	private static char direction = 'E';
	private static ArrayList<Integer> waypoint = new ArrayList<>();
	private static ArrayList<String> directionList = new ArrayList<>();
	
	public static ArrayList<Integer> updatePosition(ArrayList<Integer> oldPosition,char oldDirection , String order){
		char charOrder = order.charAt(0);
		int numberInfo = Integer.parseInt(order.substring(1));
		
		ArrayList<Integer> newPostion = new ArrayList<>(oldPosition);
		if(charOrder == 'R' || charOrder == 'L') {
			direction = rotationChange(oldDirection, charOrder, numberInfo);
		}
		else {
			char boatDirection = (charOrder == 'F') ? oldDirection : charOrder;
			switch(boatDirection) {
			case 'N':
				newPostion.set(1, newPostion.get(1) +numberInfo);
				break;
			case 'S':
				newPostion.set(1, newPostion.get(1) -numberInfo);
				break;
			case 'E':
				newPostion.set(0, newPostion.get(0) +numberInfo);
				break;
			case 'W':
				newPostion.set(0, newPostion.get(0) -numberInfo);
				break;
			}
		}
		
		return newPostion;
	}
	
	public static ArrayList<Integer> updatePositionTwo(ArrayList<Integer> oldPosition , String order){
		char charOrder = order.charAt(0);
		int numberInfo = Integer.parseInt(order.substring(1));
		
		ArrayList<Integer> newPostion = new ArrayList<>(oldPosition);
		if(charOrder == 'R' || charOrder == 'L') {
			ArrayList<String> newDirectionList = new ArrayList<>(directionList);
			for(int i = 0; i<newDirectionList.size(); i++) {
				String dir = new StringBuilder().append(rotationChange(newDirectionList.get(i).charAt(0), charOrder, numberInfo)).toString();
				newDirectionList.set(i, dir);
			}
			directionList = newDirectionList;
		// This part can also be done nice, lots of rows that could be made nicer.
		} else {
			
			int xIndex = 0;
			int yIndex = 1;
			
			if(directionList.get(0).equals("S") || directionList.get(0).equals("N")) {
				xIndex = 1;
				yIndex = 0;
			}
			int value = 0;
			switch(charOrder) {
			
			case 'N':
				value = (directionList.get(yIndex).equals("N") ? waypoint.get(yIndex) +numberInfo : waypoint.get(yIndex) -numberInfo);
				waypoint.set(yIndex, value);
				break;
			case 'S':
				value = (directionList.get(yIndex).equals("S") ? waypoint.get(yIndex) +numberInfo : waypoint.get(yIndex) -numberInfo);
				waypoint.set(yIndex, value);
				break;
			case 'E':
				value = (directionList.get(xIndex).equals("E") ? waypoint.get(xIndex) +numberInfo : waypoint.get(xIndex) -numberInfo);
				waypoint.set(xIndex, value);
				break;
			case 'W':
				value = (directionList.get(xIndex).equals("W") ? waypoint.get(xIndex) +numberInfo : waypoint.get(xIndex) -numberInfo);
				waypoint.set(xIndex, value);
				break;
			case 'F':
				for(int i = 0; i < numberInfo; i++) {
					for(int j = 0; j < waypoint.size(); j++) {
						int forwardIndex = (directionList.get(j).equals("N") || directionList.get(j).equals("S")) ? 1 : 0;
						newPostion.set(forwardIndex, coordinateValueChange(directionList.get(j), newPostion.get(forwardIndex), waypoint.get(j)));
					}
				}
				break;
			}
		}
		return newPostion;
	}
	
	
	// This function can be made way smoother, case 1 and 3 is way too similiar.
	public static char rotationChange(char currentDir,char direction, int rotation) {
		int rotationValue = rotation/90;
		
		switch(rotationValue) {
		case 0:
			return direction;
		case 1: 
			if(direction == 'R') {
				switch(currentDir) {
				case 'N':
					return 'E';
				case 'E':
					return 'S';
				case 'S': 
					return 'W';
				case 'W':
					return 'N';
				} 
				}
			else {
				switch(currentDir) {
				case 'N':
					return 'W';
				case 'W':
					return 'S';
				case 'S': 
					return 'E';
				case 'E':
					return 'N';
				}
			}
		case 2:
			switch(currentDir) {
			case 'N':
				return 'S';
			case 'W':
				return 'E';
			case 'S': 
				return 'N';
			case 'E':
				return 'W';
			}
		case 3:
			if(direction == 'R') {
				switch(currentDir) {
				case 'N':
					return 'W';
				case 'E':
					return 'N';
				case 'S': 
					return 'E';
				case 'W':
					return 'S';
				} 
				}
			else {
				switch(currentDir) {
				case 'N':
					return 'E';
				case 'W':
					return 'N';
				case 'S': 
					return 'W';
				case 'E':
					return 'S';
				}
			}
		default: 
			return direction;
		}
		
	}
	
	public static int coordinateValueChange(String dir, int oldValue, int valueChange) {
		if(dir.equals("S") || dir.equals("W")) {
			return oldValue - valueChange;
		} else {
			return oldValue + valueChange;
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		 ArrayList<String> lines = DataHandling.readStringsBySeparatorRaw("inputFiles/inputDay12.txt");
		 ArrayList<Integer> postion = new ArrayList<>();
		 postion.add(0);
		 postion.add(0);
		 int distance = 0;
		 for(String line : lines) {
			 postion = updatePosition(postion, direction, line);
		 }
		 for(int coordinate : postion) {
			 distance += Math.abs(coordinate);
		 }
		 System.out.println("The distance for part 1 is: "+distance);
		 
		 // Part 2
		 waypoint.add(10);
		 waypoint.add(1);
		 directionList.add("E");
		 directionList.add("N");
		 postion.set(0, 0);
		 postion.set(1, 0);
		 distance = 0;
		 for(String line : lines) {
			 postion = updatePositionTwo(postion, line);
		 }
		 for(int coordinate : postion) {
			 distance += Math.abs(coordinate);
		 }
		 System.out.println("The Real distance is for part 2: "+distance);
		 }
}
