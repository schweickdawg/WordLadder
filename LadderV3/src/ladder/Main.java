/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package ladder;


import java.util.ArrayList;
/**
 *
 * @author schwei
 * using WordsShort.txt:  blog -> star
 *  start -> shock      rest -> fear        try -> soy
 *  cook -> food        mom -> hug          peak -> read
 *  two -> man          happy -> slurp      dry -> poo
 *
 */
public class Main {

    static int lastColChanged=0;
    static int workingArrayCounter=0;
    static int previousWordsCount=0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

   EasyReader inFile = new EasyReader("src\\ladder\\words2.txt");
		EasyReader user = new EasyReader();
		ArrayList previousWords = new ArrayList();
		ArrayList workingArray = new ArrayList();
		ArrayList wordBank = new ArrayList();
                ArrayList ladderAnswer = new ArrayList();
		String startWord, endWord, junk, newWord, currWord;
		int resp, place=0, waSize, counterTime=0;
		int wordCounter = 0, pwCounter=0, pwCounter2=0, previousWordCounter=0;
                        while(!inFile.eof())
		{
			wordBank.add(inFile.readLine());
			wordCounter++;
		}
		inFile.close();
		do{
			System.out.println("Please enter your starting word: ");
			startWord = user.readLine();
			if(!findEntry(startWord, wordBank, wordCounter))
			{
				System.out.println("Unable to find the word, would you like to add it to the dictionary? (1-yes 2-no)");
				resp = user.readInt();
				junk=user.readLine();
				if(resp==1)
				{
					//add to dictionary
					addWordToDictionary(wordBank, startWord, wordCounter);
					wordCounter++;

				}
				else
				{
					System.out.println("Program terminated!");
					return;
				}
			}
                                System.out.println("Please enter the ending word: ");
                                endWord = user.readLine();
                                System.out.println("\n\nThe word ladder is:");
			if(!findEntry(endWord, wordBank, wordCounter))
			{
				System.out.println("Unable to find the word, would you like to add it to the dictionary? (1-yes 2-no)");
				resp = user.readInt();
				junk=user.readLine();
				if(resp==1)
				{
					//add to dictionary
					addWordToDictionary(wordBank, endWord, wordCounter);
					wordCounter++;
				}
				else
				{
					System.out.println("Program terminated!");
					return;
				}
			}
			if(startWord.length()!=endWord.length())
				System.out.println("The two words are not of the same length!!");
		}while(startWord.length()!=endWord.length());
                previousWords.add(previousWordsCount, startWord);
                previousWordsCount++;
                ladderAnswer.add(0, startWord);
                pwCounter++;
		currWord=startWord;
		//all words are valid, work through ladder here
		//		workingArray[]
		String oldCurrWord=currWord;
               	waSize=makeWorkingArray(workingArray, wordBank, wordCounter, currWord.length());
		while((!currWord.equals(endWord)))//&&(pwCounter<10))
		{
                   //twist System.out.println("Rough output " + currWord);
                    if(!ladderAnswer.contains(currWord))
                        {
                            ladderAnswer.add(currWord);//ladderAnswer.add(pwCounter2, currWord);
                            previousWords.add(currWord);
                            pwCounter2=pwCounter;
                        }
			currWord = targetNextWord(currWord, endWord, workingArray, previousWords, pwCounter);
			//pwCounter2=pwCounter;
                        if(!ladderAnswer.contains(currWord))
                        {
                            ladderAnswer.add(currWord);//ladderAnswer.add(pwCounter2, currWord);
                            previousWords.add(currWord);
                            pwCounter2=pwCounter;
                        }
                        if(pwCounter2==24500)
                        {
                            System.out.println("No ladder seems to exist.");
                            return;
                        }
                        if(oldCurrWord.equals(currWord))
			{ //call a recursive function here?
                           // pwCounter--;  //this is added last ******************
                            if((pwCounter2>0) || (startWord.equals(currWord)))
                            {
                               // System.out.println("Call to deep search");
                                //pwCounter2--;
                                currWord=deepSearch( currWord,  workingArray, previousWords, pwCounter2);
                                pwCounter++;
                                //ladderAnswer.remove(pwCounter2);
                                //ladderAnswer.add(pwCounter2, currWord);
                            }
                            else
                            {
				System.out.println("No possible words found. Sorry!");
				return;
                            }
			}
			else
                        {
				pwCounter++;
                                pwCounter2++;
                        }
		        oldCurrWord=currWord;
		}
                for(int j = 0; j<ladderAnswer.size(); j++)
                    System.out.println(ladderAnswer.get(j));
                System.out.println("There were "+ (pwCounter2) + " words in the ladder.");
                return;
	}

        static String deepSearch(String currWord, ArrayList workingArray, ArrayList previousWords, int pwCounter)
        {
            //String word="";
           // System.out.println("Entered Deep Search");
            currWord = findNextWord(currWord, workingArray, previousWords, pwCounter);
           // System.out.println("Deep Search = " + currWord);
//            for(int g = 0; g<pwCounter; g++)
//                System.out.println(previousWords[g]);
            //if(currWord.equals("chat"))
              //  System.out.println("here");
            return currWord;
        }


	static String findNextWord(String currWord, ArrayList workingArray, ArrayList previousWords, int pwCounter)
	{
		int lastCol=currWord.length();
		int targetValue=0, f=0;
		String nextWord="", temp="";
                for(int h = 0; h<workingArrayCounter; h++)
		{//Don't place duplicates in previousWords array
			targetValue=0;
			for(int cols=0; cols<=lastCol-1; cols++)
			{	//find next word in dictionary
				if(currWord.charAt(cols)==workingArray.get(h).toString().charAt(cols))
					targetValue++;
			}
			if(targetValue==lastCol-1)
			{	//next word found
                		for(int check = 0; check<previousWordsCount; check++)
				{	//need to get accurate count of words for pwCounter
					if(previousWords.get(check).equals(workingArray.get(h).toString())) //currWord
						targetValue=0;
				}
				if(targetValue!=0)
				{
                                        //previousWordsCount--;
					previousWords.add(previousWordsCount, workingArray.get(h));
                                        previousWordsCount++;//??????
					return workingArray.get(h).toString();
				}
			}
		}
                System.out.println(currWord);
		return currWord;
	}
	static boolean findEntry(String word, ArrayList array, int sizeOfArray)
	{
		int startPoint = 0, endPoint=sizeOfArray, midPoint = sizeOfArray/2;
		String wordsLower="", wordLower;
		wordLower=word.toLowerCase();
		while((!wordLower.equals(wordsLower)) && (startPoint!=midPoint)&&(endPoint !=midPoint))
		{
                if(0>array.get(midPoint).toString().toLowerCase().compareTo(wordLower))
		{
			startPoint = midPoint;
			midPoint = (endPoint+startPoint)/2;
		}
		else if(0<array.get(midPoint).toString().toLowerCase().compareTo(wordLower))
		{
			endPoint = midPoint;
			midPoint = (endPoint+startPoint)/2;
		}
		else if(array.get(midPoint).toString().toLowerCase().compareTo(wordLower)==0)
			return true;
            }
            //System.out.println(sizeOfArray);
            return false;
	}
	static void addWordToDictionary(ArrayList wordBank, String startWord, int sizeOfArray)
	{
		String lowWord=startWord.toLowerCase();
		int y=0;
		while(lowWord.compareTo(wordBank.get(y).toString().toLowerCase())>0)
		{
			y++;
		}
		for(int t = sizeOfArray; t>y; t--)
			wordBank.add(t,wordBank.get(t-1));
		wordBank.add(y, startWord);
                //System.out.println("y == " + y);
		sizeOfArray++;
		EasyWriter out = new EasyWriter("C:\\Documents and Settings\\schwei\\My Documents\\NetBeansProjects\\Ladder\\src\\ladder\\wordsshort.txt");
		for(int h = 0; h<sizeOfArray-1; h++)
			out.println(wordBank.get(h));
		out.close();
	}
	static int makeWorkingArray(ArrayList workingArray, ArrayList wordBank,int bankLength, int len)
	{ //return size of new array
		String temp;
		int waSize=0, currSize;
		/*for(int u = 0; u<10; u++)
			System.out.println(wordBank[u]);
		System.out.println(bankLength);
		*/for(int j = 1; j<bankLength-1; j++)
		{
			temp = wordBank.get(j).toString();
			currSize=temp.length();
			if(currSize==len)
			{
				workingArray.add(waSize, wordBank.get(j).toString().toLowerCase());
				waSize++;
			}
		}
		workingArrayCounter = waSize;
		/*for(int i = 0; i<waSize; i++)
			System.out.println(workingArray[i]);
		*/return waSize;
	}
        static String targetNextWord(String currWord, String endWord, ArrayList workingArray, ArrayList previousWords, int pwCounter)
        {
          int lastCol=currWord.length();
	  int targetValue=0;
          int sameLetters=0;
          ArrayList possibleWords = new ArrayList();
          int nextPossibleWordCounter=0;
	  String nextWord="", temp="";
          for(int j = 0; j<currWord.length();j++)
          {
              if (currWord.substring(j, j+1).equals( endWord.substring(j, j+1)))
                      sameLetters++;  //counting letter matches between current and last word
          }
	  for(int h = 0; h<workingArrayCounter; h++)
          {
              targetValue=0;
              for(int cols=0; cols<=lastCol-1; cols++)
              {	//find next word in dictionary
		if(currWord.charAt(cols)==workingArray.get(h).toString().charAt(cols))
			targetValue++;
              }
              if(targetValue==lastCol-1)
              {
                 possibleWords.add(nextPossibleWordCounter, workingArray.get(h));
                 nextPossibleWordCounter++;
              }

          }
          for(int y = 0; y<nextPossibleWordCounter; y++)
          {
            targetValue=0;
            for(int col=0; col<lastCol; col++)
            {  //sameLetters is count of matches
              if(endWord.charAt(col)==possibleWords.get(y).toString().charAt(col))
                    targetValue++;
                 if(targetValue==(sameLetters+1))
                 {
                     currWord=possibleWords.get(y).toString();
                     if(!previousWords.contains(currWord))
                         return currWord;
                 }
             }
          }
          //need to deal with previous word use
          return currWord; //if no word found
        }
}
