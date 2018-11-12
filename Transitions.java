package Theoretische_Informatik;

public class Transitions {	
	private Synchronisation synch;
	
	private int[][] transitions;
	private int alphabetLength;
	private int nodes;
	private int wordCount = 0;
	
	public Transitions(int alphabetLength, int nodes, int wordLength){
		this.alphabetLength = alphabetLength;
		this.nodes = nodes;

		getTransitions();
		synch = new Synchronisation(transitions, wordLength, nodes, alphabetLength);
		connectNodes(new int[nodes], 0);		
	}
	
	public int getWordCount(){
		return this.wordCount;
	}
	
	private void getTransitions(){
		transitions = new int[(int)Math.pow(nodes,alphabetLength)][alphabetLength];
		for(int i = 0; i < (int)Math.pow(nodes,alphabetLength); i++){
			transitions[i][alphabetLength - 1] = i % nodes;

			for(int j = 1; j < alphabetLength; j++){
				transitions[i][alphabetLength - 1 - j] = ((i - transitions[i][alphabetLength - j]) % (int)Math.pow(nodes,j + 1))/(int)Math.pow(nodes,  j);			
			}
			System.out.print(i + ": ");
			for(int j = 0; j < alphabetLength; j++){
				System.out.print(transitions[i][j] + " ");	
			}		
			System.out.print("\n");
		}		
	}
	
	private void connectNodes(int[] values, int length){
		if(length == nodes){
			wordCount += synch.checkSynchronisation(values);			
		}
		else{
			for(int i = 0; i < (int)Math.pow(nodes,alphabetLength); i++){
				values[length] = i;
				connectNodes(values, length + 1);
			}
		}
	}
}
