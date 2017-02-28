package com.company;

/**
 * Created by JBrannan on 10/12/2016.
 */
public class dictionary {

    public static String txtWordCaps(String txtWord) {

        boolean startsCapital = false; //initialise boolean to store whether string starts with capital
        boolean hasCapital = false; //initialise boolean to store whether string contains capital
        boolean hasPunct = false; //initialise boolean to store whether string contains punctuation
        boolean endPunct = false; //initialise boolean to store whether string ends with punctuations
        boolean punctIteration; //declare boolean which is used to determine whether current character in loop is a punctuation mark
        String txtWordNoPunct = ""; //initialise string which will contain original string without any punctuation
        String txtWordOnlyEndPunct = ""; //initialise string which will contain original without punctuation except last character
        char charTxtWord[] = new char[txtWord.length()]; //initialise character array to length of string which will contain
        //individuals characters in string
        char capsLetters[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'X', 'Y', 'Z'}; // Stores all capital letters in alphabet
        char punctuation[] = {'.', ',', ';', ':', '?', '!', '"', ')', '('}; //Store common punctuation marks

        // This nested for loop will store each character in string in character array.
        // It will then compare each character to all capital letters.
        for (int i = 0; i < txtWord.length(); i++) {
            charTxtWord[i] = txtWord.charAt(i);
            for (int j = 0; j < 26; j++) {
                //if first character is a capital letter, set startsCapital to true.
                if (charTxtWord[0] == capsLetters[j]) {
                    startsCapital = true;

                } //else if character is a capital letter, set hasCapital to true.
                else if (charTxtWord[i] == capsLetters[j]) {
                    hasCapital = true;
                }
            }
        }

        //This nested for loop will compare each character in string to punctuation array to work out if
        //character is one of the predetermined punctuation marks.
        for (int i = 0; i < txtWord.length(); i++)
        {
            punctIteration = false; //initialise each character of string
            for (int j = 0; j < 9; j++)
            {
                // If i == to last character in string and == to one of the punctuations marks,
                // set endPunct to true and punctIteration to true
                if (i == txtWord.length()-1 && charTxtWord[i] == punctuation[j]) {
                endPunct = true;
                punctIteration = true;
                }
                // Else if character other than last in string == to one of the punctuation marks,
                // set hasPunct to true and punctIteration to true
                else if(charTxtWord[i] == punctuation[j])
                {
                    hasPunct = true;
                    punctIteration = true;
                }
                // If last character in punctuation array and punctIteration == true (contains punctuation in this
                // character of the string), txtWordOnlyEndPunct = txtWordNoPunct + character
                if(j == 8 && punctIteration == true)
                {
                    txtWordOnlyEndPunct = txtWordNoPunct + String.valueOf(charTxtWord[i]);
                    System.out.println("ALERT TEST ALERT" + txtWordOnlyEndPunct);
                }
                // If last character in punctuation array and punctIteration == false, add character to string txtWordNoPunct
                else if(j == 8 && punctIteration == false)
                {
                    txtWordNoPunct = txtWordNoPunct + String.valueOf(charTxtWord[i]);
                }
            }
        }

        //System.out.println("Debug: " +txtWord);
        //System.out.println("Debug: " +txtWordNoPunct);

        // Input word to all lower case. If it is in the dictionary then continue as spelling is correct and doesnt contain punctuation
        if (LevenshteinSpellCheck.dictWords.contains(txtWord.toLowerCase())) {
            //System.out.println("Debug: "+ txtWord);
            //System.out.println("Debug: " + txtWord);

            //if input word starts with capital letter but contains no capitals, return.
            if (startsCapital == true && hasCapital == false) {
                //System.out.println("debug3: "+ txtWord);
                return txtWord;

                // Else if input word contains capital but does not start with one, add original word plus lower case suggestion
                // to mispelt words array. Add to mispeltwordscount then return.
            } else if (startsCapital == false && hasCapital == true) {
                //System.out.println("Debug");
                LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][0] = txtWord;
                LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][1] = txtWord.toLowerCase();
                LevenshteinSpellCheck.mispeltWordsCount = LevenshteinSpellCheck.mispeltWordsCount + 1;
                return txtWord;

                //Else if starts with capital and cotains capital, add original word to mispelt words
            } else if (startsCapital == true && hasCapital == true) {
                LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][0] = txtWord;

                char tempTxtWordChar = txtWord.charAt(0) ; //Add first character (capital letter) to tempTxtWordChar
                String tempTxtWordString = ""; //Initialise tempTxtWordString
                String txtWord2 = String.valueOf(tempTxtWordChar); //Initialise txtWord2 and add first character to string

                //from character 2 of input word to last character, store lower case of character in txtWord2 string.
                for (int i = 1; i < txtWord.length(); i++) {
                    tempTxtWordChar = txtWord.charAt(i);
                    tempTxtWordString = String.valueOf(tempTxtWordChar);
                    tempTxtWordString = tempTxtWordString.toLowerCase();
                    txtWord2 = txtWord2 + tempTxtWordString;
                }

                //Add txtWord2 (First character capital letter, rest lower case) to suggestions and lower case version
                //Then add 1 to mispeltwordscount and return.
                LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][1] = txtWord2;
                LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][2] = txtWord.toLowerCase();
                LevenshteinSpellCheck.mispeltWordsCount = LevenshteinSpellCheck.mispeltWordsCount + 1;
                return txtWord;
            }
        }

        //System.out.println("Debug");

        //If dictionary contains input word without punctuation continue
         if (LevenshteinSpellCheck.dictWords.contains(txtWordNoPunct.toLowerCase()))
         {
             //if input word ends with punctuation mark continue
            if (endPunct == true) {
                // If input word does not contain any other punctuation continue
                if (hasPunct == false) {
                    // If input word contains capital letter but does not start with one, add original word and suggest lower case
                    //version to mispeltwords. Then add 1 to mispeltwordscount and return.
                    if (hasCapital == true && startsCapital == false) {
                        LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][0] = txtWord;
                        LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][1] = txtWord.toLowerCase();
                        LevenshteinSpellCheck.mispeltWordsCount = LevenshteinSpellCheck.mispeltWordsCount + 1;
                        return txtWord;
                        //Else if starts with capital and contains capital continue
                    } else if (hasCapital == true && startsCapital == true) {
                        //System.out.println("Debug");
                        char tempTxtWordChar = txtWord.charAt(0);
                        //Add first character to txtWord2 string
                        String tempTxtWordString = "";
                        String txtWord2 = String.valueOf(tempTxtWordChar);
                        for (int i = 1; i < txtWord.length(); i++) {
                            //add rest of characters to txtWord2 with all other letters lowercase
                            tempTxtWordChar = txtWord.charAt(i);
                            tempTxtWordString = String.valueOf(tempTxtWordChar);
                            tempTxtWordString = tempTxtWordString.toLowerCase();
                            txtWord2 = txtWord2 + tempTxtWordString;
                        }
                        //Add txtWord2 (First character capital letter, rest lower case) to suggestions and lower case version
                        //Then add 1 to mispeltwordscount and return.
                        LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][0] = txtWord;
                        LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][1] = txtWord2;
                        LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][2] = txtWord.toLowerCase();
                        LevenshteinSpellCheck.mispeltWordsCount = LevenshteinSpellCheck.mispeltWordsCount + 1;
                        return txtWord;
                    }
                }
                //Else if input word ends with punctuations and contains punctuation continue
                else if (endPunct == true && hasPunct == true)
                {
                    //if starts with capital, add original word and suggest txtwordOnlyEndPunct
                    //then add 1 to mispelt words count and return
                    if (startsCapital == true && hasCapital == false)
                    {
                        LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][0] = txtWord;
                        LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][1] = txtWordOnlyEndPunct;
                        LevenshteinSpellCheck.mispeltWordsCount = LevenshteinSpellCheck.mispeltWordsCount + 1;
                        return txtWord;
                    }
                    //else if contains capital but does not start with one, add original word and suggest lowercase version
                    //of txtWord. Then add 1 to mispeltwordscount and return
                    else if (hasCapital == true && startsCapital == false) {
                        LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][0] = txtWord;
                        LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][1] = txtWordOnlyEndPunct.toLowerCase();
                        LevenshteinSpellCheck.mispeltWordsCount = LevenshteinSpellCheck.mispeltWordsCount + 1;
                        return txtWord;
                    }
                    //else if has capital and starts with capital continue
                    else if (hasCapital == true && startsCapital == true) {
                        //System.out.println("Debug");
                        //Add first character (capital) to string txtword2
                        char tempTxtWordChar = txtWord.charAt(0);
                        String tempTxtWordString = "";
                        String txtWord2 = String.valueOf(tempTxtWordChar);
                        //add lower case character from second character to end to string txtWord2
                        for (int i = 1; i < txtWordOnlyEndPunct.length(); i++) {
                            tempTxtWordChar = txtWordOnlyEndPunct.charAt(i);
                            tempTxtWordString = String.valueOf(tempTxtWordChar);
                            tempTxtWordString = tempTxtWordString.toLowerCase();
                            txtWord2 = txtWord2 + tempTxtWordString;
                        }
                        //add original word and suggest only first character capital and lowercase version to mispeltwords.
                        //then add 1 to mispelt words count and return.
                        LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][0] = txtWord;
                        LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][1] = txtWord2;
                        LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][2] = txtWord2.toLowerCase();
                        LevenshteinSpellCheck.mispeltWordsCount = LevenshteinSpellCheck.mispeltWordsCount + 1;
                        return txtWord;
                    }
                    else
                    {
                        //else add original word and suggest txtWordOnlyEndPunct to lower case.
                        //Then add 1 to mispelt words and return.
                        LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][0] = txtWord;
                        LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][1] = txtWordOnlyEndPunct.toLowerCase();
                        LevenshteinSpellCheck.mispeltWordsCount = LevenshteinSpellCheck.mispeltWordsCount + 1;
                        return txtWord;
                    }
                }
                return txtWord;
            }
        }
        //System.out.println("Debug");


        int distSugWord[] = new int[3];
        distSugWord[0] = 20; //Initialise int array with large distant starter numbers
        distSugWord[1] = 20;
        distSugWord[2] = 20;

        LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][0]
                = txtWord; //Add original to mispelt words
        //System.out.println("debug4: "+ txtWord);

        // if input word has made it this far, word is not in the dictionary. For loop to go through entire dictionary
        for (int i = 0; i < LevenshteinSpellCheck.dictWords.size(); i++) {
            //Find distance between current dictionary word and input word
            int distance = Levenshtein.levDist(txtWord, LevenshteinSpellCheck.dictWords.get(i));
            if (distance < distSugWord[0]) {
                //if distance from current dictionary word and input word is less than current distance,
                //add word to suggest words and ammend distance integer to int array.
                LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][1]
                        = LevenshteinSpellCheck.dictWords.get(i);
                distSugWord[0] = distance;
            } else if (distance < distSugWord[1]) {
                //else if distance from current dictionary word and input word is less than current distance,
                //add word to suggest words and ammend distance integer to int array.
                LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][2]
                        = LevenshteinSpellCheck.dictWords.get(i);
                distSugWord[1] = distance;
            } else if (distance < distSugWord[2]) {
                //else if distance from current dictionary word and input word is less than current distance,
                //add word to suggest words and ammend distance integer to int array.
                LevenshteinSpellCheck.mispeltWords[LevenshteinSpellCheck.mispeltWordsCount][3]
                        = LevenshteinSpellCheck.dictWords.get(i);
                distSugWord[2] = distance;
            }
        }
        //Add 1 to mispeltwordscount and return.
        LevenshteinSpellCheck.mispeltWordsCount = LevenshteinSpellCheck.mispeltWordsCount + 1;
        return txtWord;
    }
}
