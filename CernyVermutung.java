package Theoretische_Informatik;

public class CernyVermutung {

	private static int alphabet = 3;
	private static int nodes = 4;
	private static int wordLength = 5;
	
	public static void main(String[] args) {
		Transitions transitions = new Transitions(alphabet, nodes, wordLength);
		System.out.println("Words: " + transitions.getWordCount());
	}	
}
