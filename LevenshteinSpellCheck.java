package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;
import java.awt.*;
import java.io	.*;

/**
 * Created by JBrannan on 10/12/2016.
 */
public class LevenshteinSpellCheck {

    public static ArrayList<String> dictWords = new ArrayList(); //Words in dictionary stored in arraylist
    public static String mispeltWords[][] = new String[200][4]; //Stores mispelled words plus suggestions
    public static int mispeltWordsCount = 0; //counts the amount of mispelled words

    public static void main(String[] args) {

        File dictFile = new File("/Users/JBrannan/Documents/LevenshteinSpellCheck/src/com/company/dictionary.txt");
        try (Scanner dictscan = new Scanner(new FileInputStream(dictFile))) {
            //scans the words within "dictFile"
            //whilst there is a next string in the dictionary file, it will add the string into the ArrayList
            while (dictscan.hasNext()) {
                dictWords.add(dictscan.next());
            }
        } catch (FileNotFoundException e) {
            //if exception, print file not found
            System.out.println("File not found");
        }

        ArrayList<String> outputWords = new ArrayList();
        //stored output words in arraylist
        File inputFile = new File("/Users/JBrannan/Documents/LevenshteinSpellCheck/src/com/company/input.txt");

        try (Scanner txtFile = new Scanner(new FileInputStream(inputFile))) {
            //whilst there is a next line in the input file
            while (txtFile.hasNextLine()) {
                String[] words = txtFile.nextLine().split(" ");
                //split strings in the line by spaces
                int whileCount = 0; //counts the amount of iterations for the below for loop which is reset to 0 when exit for loop
                for(int i = 0; i < words.length; i++)
                {
                    //System.out.println("Debug: "+words[whileCount]); //debug
                    String txtFileString = words[whileCount];

                    //each iteration of the for loop, txtFileString will be assigned to a string in the line read by the scanner
                    //if dictionary arraylist contains the word, add word to output arraylist
                    //else run txtWordCaps method in the dictionary class, then add the string to output arraylist

                    if (dictWords.contains(txtFileString)) {
                        outputWords.add(txtFileString);
                    } else {
                        String outputWord = dictionary.txtWordCaps(txtFileString);
                        //System.out.println("Debug2: " + test);
                        outputWords.add(outputWord);
                    }
                    //add 1 to the whilecount int
                    whileCount = whileCount+ 1;
                }
                //if there is a next line in the input, add "/n" to outputwords
                if (txtFile.hasNextLine()) {
                    outputWords.add("/n");
                }
            }
            //outputWords.remove(outputWords.size()-1);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        int count = 0; //counts the amount of mispelled words

        //if mispelled words is not equal to null, display text. Else, display a different text
        if (mispeltWords[0][0] != null) {
            System.out.println("Spelling mistakes were found in your file.");
            System.out.println("Please type the correct word into the console to replace the one in your file.");
            System.out.println("If you want to add the original word into the local dictionary, enter <add> to add it.");
            System.out.println("If the original word is incorrect but a suitable replacement has not been suggested, " +
                    " please enter a replacement word into the console.");
            System.out.println();
        } else {
            System.out.println("There were no spelling mistakes found.");
        }

        boolean dictWordAdded = false; //determines whethere any words have been added to the dictionary
        Scanner in = new Scanner(System.in);
        //whilst mispelled words are not equal to null, display the original word and suggested replacements
        while (mispeltWords[count][0] != null) {
            System.out.println("Original word: " + mispeltWords[count][0]);
            System.out.print("Suggested replacements: ");

            for (int i = 1; i <= 3; i++) {
                if (mispeltWords[count][i] != null) {
                    System.out.print(mispeltWords[count][i] + " ");
                }
            }
            System.out.println();
            System.out.println();

            //Take input from the console.
            String tempSt = in.next().trim();
            //System.out.println("Debug: " + tempSt);

            //for loop to match String in output word to original word in mispeltwords
                for(int i = 0; i < outputWords.size(); i++)
                {
                    if (outputWords.get(i) == mispeltWords[count][0])
                    {
                        //if input = "<add>" continue
                        if (tempSt.equals("<add>"))
                        {
                            //System.out.println("Debugging2");
                            //Store word to add as string dictTemp
                            String dictTemp = mispeltWords[count][0];
                            //if last character of dictTemp == . ? or ! continue
                            if (dictTemp.charAt(dictTemp.length()-1) == '.' || dictTemp.charAt(dictTemp.length()-1) == '?'
                            || dictTemp.charAt(dictTemp.length()-1) == '!')
                            {
                                //System.out.println("DEBUGGING");
                                //Intialise String dictNoPunct, remove punctuation from end of string then add to dictionary
                                String dictNoPunct = "";
                                for(int j = 0; j < dictTemp.length()-1; j++)
                                {
                                    dictNoPunct = dictNoPunct + dictTemp.charAt(j);
                                }
                                dictWords.add(dictNoPunct);
                                outputWords.set(i, mispeltWords[count][0]);
                                dictWordAdded = true;
                            }
                            else
                            //else add word to dictionary, set output word to original word and
                            //mark boolean dictWordAdded to true
                            {
                                //System.out.println("Debugging3");
                                dictWords.add(mispeltWords[count][0]);
                                outputWords.set(i, mispeltWords[count][0]);
                                dictWordAdded = true;
                            }
                        }
                        //else set output word to input from console
                        else
                        {
                            outputWords.set(i, tempSt);
                        }
                    }
                }

            count = count + 1; //add 1 to count
        }
        String stringOutput;
        try{
            File file = new File("/Users/JBrannan/Documents/LevenshteinSpellCheck/src/com/company/output.txt");
                //set up new printwrite with the path = to file.
            PrintWriter outputPrint = new PrintWriter(file);

            file.createNewFile();
            //outputPrint.flush();
                //create new file
            for(int i = 0; i < outputWords.size(); i++)
            {
                //if i is equal to the the last word in the output list array, display output to console
                //and write to output file
                if (i == outputWords.size()-1) {
                    stringOutput = outputWords.get(i);
                    System.out.print(stringOutput);
                    outputPrint.print(stringOutput);
                }
                //else if the output in the listarray is equal to "/n", println to console and output file
                else if (outputWords.get(i) == "/n")
                {
                    System.out.println();
                    outputPrint.println();
                }
                //else display output word + " " to console and write to output file
                else {
                    stringOutput = outputWords.get(i) + " ";
                    System.out.print(stringOutput);
                    outputPrint.print(stringOutput + " ");
                }
            }

            //close printwriter
            outputPrint.close();

            //System.out.println("Data successfully appended at the end of file");

        } //if out of bounds exception, display Exception occurred.
        catch(IOException ioe){
            System.out.println("Exception occurred.");
        }

        if(dictWordAdded == true)
        // if boolean dictWordAdded is true, sor dictwords, in alphabetical order, disregarding lower case or upper case
        {
            Collections.sort(dictWords, String.CASE_INSENSITIVE_ORDER);
            try {
                File file = new File("/Users/JBrannan/Documents/LevenshteinSpellCheck/src/com/company/dictionary.txt");
                PrintWriter dictPrint = new PrintWriter(file);
                //new printwriter

                file.createNewFile();
                //dictPrint.flush();
                //create file then print all strings in the the dictionary list array to dictionary file

                for(int i = 0; i < dictWords.size(); i++)
                {
                    dictPrint.println(dictWords.get(i));
                }
                //close printwriter
                dictPrint.close();

            } catch (IOException ioe) { //if out of bounds exception, print Exception occurred.
                System.out.println("Exception occurred.");
            }
        }

    }
}
