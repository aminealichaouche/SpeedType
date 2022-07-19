package SpeedType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/*
Methods:

addSet(String term, String definition) - adds a set (term, definition) to the end of an array by copying the array and adding one more space to it
addCustomSet(String customFile) - reads in sets from custom text file and adds it to termsArray
getSetAtQuestionNum(int questionNum) - returns a String array at questionNum
getRandomSet() - returns a String array, use indexing to get either term ([0] for question, [1] for definition)
getQuestion(int questionNum) - returns a String that is the question at questionNum
getDefinition(int questionNum) - returns a String that is the definition at questionNum
getIndexOfStringArray(String[] arr) - returns index of entered array
getSize() - returns number of sets inside array
printArrays() - prints all the arrays in the 2D array
printInOrder() - prints all the arrays in a numbered format
setTermInSet(int questionNum, String whichTerm, String newTerm) - allows user to set a new term in a specific set
deleteSet(int questionNum) - deletes a specific set by creating a new 2D array with one less space
resetArray() - resets termsArray

*/

public class Terms {
    
    private String[][] termsArray;

    public Terms() {
        termsArray = new String[0][0];
    }

    public void addSet(String term, String definition) {
        String[][] temp = termsArray.clone();
        termsArray = new String[temp.length+1][2]; // overwrite the array to increase fixed size

        for (int r = 0; r < temp.length; r++) {
            for (int c = 0; c < 2; c++) {
                termsArray[r][c] = temp[r][c]; // copy all current elements to the next array
            }
        }
        termsArray[temp.length][0] = term;
        termsArray[temp.length][1] = definition;
        
    }

    public void addCustomSet(String customFile) throws FileNotFoundException {
        File file = new File(customFile);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String[][] temp = termsArray.clone();
            termsArray = new String[temp.length+1][2];

            for (int r = 0; r < temp.length; r++) {
                for (int c = 0; c < 2; c++) {
                    termsArray[r][c] = temp[r][c]; // copy all current elements to the next array
                }
            }
            termsArray[temp.length][0] = scanner.nextLine();
            if (scanner.hasNextLine()) {
                termsArray[temp.length][1] = scanner.nextLine();
            } else System.out.println("Need to add corresponding element");
            
            
        }
        scanner.close();
    }

    public String[] getSetAtQuestionNum(int questionNum) {
        return termsArray[questionNum];
    }

    public String[] getRandomSet() {
        Random random = new Random();
        int idx = random.nextInt(termsArray.length) + 1;
        

        return termsArray[idx-1];
    }

    public String getQuestion(int questionNum) {
        return termsArray[questionNum][0];
    }

    public String getDefinition(int questionNum) {
        return termsArray[questionNum][1];
    }

    public int getIndexOfStringArray(String[] arr) {
        int idx = 0;
        for (int i = 0; i < termsArray.length; i++) {
            if (arr.equals(termsArray[i])) idx = i;
        }
        return idx;
    }

    public int getSize() {
        return termsArray.length;
    }

    public void printArrays() {
        for (String[] arr: termsArray) {
            System.out.println(Arrays.toString(arr));
        }
    }

    public void printInOrder() {
        if (termsArray.length == 0) System.out.println("No Sets are inside wordbank");
        for (int r = 1; r < termsArray.length + 1; r++) {
            System.out.println(r + ": " + Arrays.toString(termsArray[r-1]));
        }
    }

    public void setTermInSet(int questionNum, String whichTerm, String newTerm) {
            int col = 0; 
            if (whichTerm.equals("question")) col = 0;
            if (whichTerm.equals("definition")) col = 1;

            termsArray[questionNum][col] = newTerm;
    }
 
    public void deleteSet(int questionNum) {
        String[][] newArr = new String[termsArray.length-1][2];

        int idx = 0;
        for (int r = 0; r < termsArray.length; r++) {
            if (r != questionNum) {
                newArr[idx] = termsArray[r];
                idx += 1;
            }
        }

        termsArray = newArr; 
    }

    public void resetArray() {
        termsArray = new String[0][0];
    }

    public static void main(String[] args) throws FileNotFoundException {
        
        Terms wordbank = new Terms();

        wordbank.addSet("what", "hello");
        wordbank.addSet("yo", "mama");
        wordbank.deleteSet(1);

        wordbank.printArrays();
        
        
        
        
    }
}