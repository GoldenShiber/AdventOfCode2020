package utils;

public class Pair {

	private String word;
	private int number;
	
	public Pair() {
		word = "";
		number = 0;
	}
	
	public Pair(String wordInput, int numberInput) {
		word = wordInput;
		number = numberInput;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
}
