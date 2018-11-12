package Theoretische_Informatik;

import java.util.ArrayList;

/** 
 * @author Egger Simon
 * 
 * The class Synchronisation is used to to calculate all possible words for the given automat
 * and check whether the word is synchronizing or not
 *
 */
public class Synchronisation {
	private int[][] transitions;
	private int alphabetLength;
	private int nodes;
	private int wordLength;
	private int wordCount;
	
	private ArrayList<int[]> correctWords;
	private int[] transitionValues;
	
	/*
	 * Initializes variables for later use
	 * 
	 * @param transitions All possible transitions for each state of the automat
	 * @param wordLength Defines the length of the words the algorithm shall check
	 * @param nodes Number of different states of the automat
	 * @param alphabetLength Length of the given alphabet
	 */
	public Synchronisation(int[][] transitions, int wordLength, int nodes, int alphabetLength){
		this.transitions = new int[transitions.length][transitions[0].length];
		for(int i = 0; i < transitions.length; i++){
			System.arraycopy(transitions[i], 0, this.transitions[i], 0, transitions[i].length);			
		}
		
		correctWords = new ArrayList<int[]>();
		this.wordLength = wordLength;
		this.nodes = nodes;
		this.alphabetLength = alphabetLength;		
	}
	
	/*
	 * Checks the given automat for different synchronizing words
	 * 
	 * @param values Array of indexes for "transitionValues" which define the automat 
	 * @return wordCount Amount of synchronizing words
	 */
	public int checkSynchronisation(int[] values){
		transitionValues = new int[values.length];
		System.arraycopy(values, 0, transitionValues, 0, values.length);
		correctWords.clear();
		wordCount = 0;
		
		boolean correct = getWords(new int[wordLength], 0);
		if(!correct){
			return 0;
		}
		return wordCount;
	}	
	
	/*
	 * Calculates all possible words regressively
	 * 
	 * @param word Array which is regressively passed on containing a word
	 * @return Boolean stating if there exists a prefix smaller then the given word, which is also synchronizing
	 */
	private boolean getWords(int[] word, int length){
		boolean correct;
		if(length == wordLength){
			correct = checkWord(word);
			return correct;
		}
		else{
			for(int i = 0; i < alphabetLength; i++){
				word[length] = i;
				correct = getWords(word, length + 1);	
				if(!correct){
					return false;
				}
			}
		}

		//Outputs the calculated results
		if(length == 0){
			for(int i = 0; i < correctWords.size(); i++){
				System.out.print("Word: ");
				for(int j = 0; j < wordLength; j++){
					System.out.print(correctWords.get(i)[j] + " ");
				}
				System.out.print("\nTransitions: ");			
				for(int j = 0; j < nodes; j++){
					System.out.print(transitionValues[j] + " ");
				}
				System.out.print("\n\n");	
			}
		}
		
		return true;
	}
	
	/*
	 * Checks whether the word is synchronizing or not	 *
	 * 
	 *  @param word Calculated word of the automat
	 *  @return 
	 */
	private boolean checkWord(int[] word){
		boolean tempcorrect = false;
		boolean correct = true;
		
		int[] resultNodes = new int[nodes];

		for(int n = 0; n < nodes; n++){
			resultNodes[n] = n;
		}

		
		for(int n = 0; n < wordLength; n++){
			for(int m = 0; m < nodes; m++){
				resultNodes[m] = transitions[transitionValues[resultNodes[m]]][word[n]];
			}
			if(n < wordLength - 1){
				tempcorrect = false;
				for(int i = 0; i < nodes; i++){
					if(resultNodes[i] != resultNodes[0]){
						tempcorrect = true;
					}
				}
				
				if(!tempcorrect){
					correct = false;
				}					
			}
		}

		if(tempcorrect && correct){
			for(int i = 0; i < nodes - 1; i++){
				if(resultNodes[i] != resultNodes[i + 1]){
					tempcorrect = false;
				}
			}

			if(tempcorrect){
				int[] temp = new int[wordLength];
				for(int i = 0; i < wordLength; i++){
					temp[i] = word[i];						
				}
				correctWords.add(temp);		
				wordCount++;
			}

			tempcorrect = true;
		}

		return correct;
	}
}
