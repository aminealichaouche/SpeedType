package SpeedType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

// IMPORTANT: you need a space at the end of every map to not combine the last ones.

public class Wordbank {
    
    private String currentString;
    private String currentModifiedString;
    private Terms maps;
    private Terms modifiedMaps;
    private ArrayList<String> usedChallenges;
    private ArrayList<String> usedChallengesModified;
    private int totalNum;
    private int totalNumModified = 0;
    public Wordbank() {
        currentString = "";
        maps = new Terms();
        modifiedMaps = new Terms();
        usedChallenges = new ArrayList<String>();
        usedChallengesModified = new ArrayList<String>();
        totalNum = maps.getSize();
        totalNumModified = modifiedMaps.getSize();
    }

    public void readFile() throws FileNotFoundException {
        File directory = new File(System.getProperty("user.dir") + "/SpeedType/maps.txt");

        Scanner scanner = new Scanner(directory);
        String currentMap = "";
        String everything = "";
        int numOfMaps = 0;

        while (scanner.hasNextLine()) {
            String check = scanner.nextLine();
            //System.out.println("check: " + "(" +check + ")");

            // if (check.equals("")) {
                
            // }
            boolean isInteger = true;
            try {
                Integer.parseInt(check.split(" ")[0].substring(0, check.split(" ")[0].length()-1));
                //System.out.println(check.split(" ")[0].substring(0, check.split(" ")[0].length()-1));
                isInteger = true;
            } catch (Exception e) {
                isInteger = false;
            }
            if (isInteger) {
                maps.addSet(String.valueOf(numOfMaps+1), check);  
                String temp = maps.getDefinition(numOfMaps);
                String[] tempArray = temp.split(" ");
                String[] modifiedArray = new String[tempArray.length-1];
                String newString = "";
                for (int i = 1; i < tempArray.length; i++) {
                    modifiedArray[i-1] = tempArray[i]; 
                    newString += modifiedArray[i-1] + " ";
                }
                newString = newString.strip();

                maps.setTermInSet(numOfMaps, "definition", newString);
                numOfMaps++;
            }
      
            

            
        }
        scanner.close();
        cloneMaps(modifiedMaps);
    }


    public void readFileMethod2() throws FileNotFoundException {
        File directory = new File(System.getProperty("user.dir") + "/SpeedType/maps.txt");

        Scanner scanner = new Scanner(directory);
        String currentMap = "";
        String everything = "";

        
        while (scanner.hasNextLine()) {
            String check = scanner.nextLine();
            System.out.println("check: " + "(" +check + ")");

            everything += check;         
        }


        String[] everythingSplit = everything.split(" ");
        System.out.println("everything split: " + Arrays.toString(everythingSplit));

        
        System.out.println(Arrays.toString(everythingSplit));
        System.out.println(currentMap);
        for (String str: everythingSplit) {
            currentMap += str + " ";
        }

        int numOfMaps = 0;
        for (String str: everythingSplit) {
            if (str.length() >= 3) {
                if (Character.isDigit(str.charAt(0)) && str.charAt(1) == 'M' && str.charAt(2) == '.') {
                    //System.out.println("inside");
                    numOfMaps++;
                }
            }
        }
        for (int i = 0; i < numOfMaps; i++) {
            if (i == numOfMaps - 1) { // if you've reached the end
                maps.addSet(String.valueOf(i+1), currentMap.substring(currentMap.indexOf((i + 1) + "M."), currentMap.length()));
            } else {
                maps.addSet(String.valueOf(i+1), currentMap.substring(currentMap.indexOf((i + 1) + "M."), currentMap.indexOf((i + 2) + "M.")));
            }
            
        }
        for (int i = 0; i < maps.getSize(); i++) {
            String temp = maps.getDefinition(i);
            

            maps.setTermInSet(i, "definition", temp.substring(0, temp.length()-1).substring(temp.indexOf('.') + 2));
        }
        
        

        scanner.close();
    }

    public void randomizeString() {
        if (maps.getSize() > 0) {
            Random random = new Random();
            currentString = maps.getDefinition(random.nextInt(maps.getSize()));
        }
    }

    public void guaranteeRandom() {
        if (maps.getSize() > 0) {
            Random random = new Random();

            if (totalNum == maps.getSize()) {
                System.out.println("inside");
                usedChallenges = new ArrayList<String>();
                totalNum = 0;
            }

            while (true) {
                currentString = maps.getDefinition(random.nextInt(maps.getSize()));
                if (!usedChallenges.contains(currentString)) { // keep randomizing until a unique string appears
                    usedChallenges.add(currentString);
                    break;
                }
            }
            totalNum++;
        }
    }

    public String getCurrentString() {
        return currentString;
    }

    public Terms getMaps() {
        return maps;
    }

    public Terms getModifiedMaps() {
        return modifiedMaps;
    }

    public void cloneMaps(Terms other) {
        other.resetArray();

        for (int r = 0; r < maps.getSize(); r++) { // make modified maps the same as maps
            other.addSet(maps.getQuestion(r), maps.getDefinition(r));
        }
    }

    public void updateModifiedMaps(Map<String, String> settings) {
        cloneMaps(modifiedMaps);
        if (settings.get("Have Punctuation").equals("false")) {
            System.out.println("Will be modifying");
            for (int i = 0; i < modifiedMaps.getSize(); i++) {
                String temp = modifiedMaps.getDefinition(i);

                String ns = "";
                for (String str: temp.split(" ")) {
                    for (char ch: str.toCharArray()) {
                        if (ch != '.' && ch != ',' && ch != '!' && ch != '?') {
                            ns += ch;
                        }
                        
                    }
                    ns += " ";
                }
                ns = ns.strip();
                modifiedMaps.setTermInSet(i, "definition", ns.toLowerCase());
            }
        }
        if (settings.get("Time/Word Mode").equals("time")) {
            // TODO inside TestThatWorks
        } else if (settings.get("Time/Word Mode").equals("word")) {
            int lengthToHave = 0;
            if (settings.get("SelectedNumOfWords").equals("1")) {
                lengthToHave = 10;
            }
            else if (settings.get("SelectedNumOfWords").equals("2")) {
                lengthToHave = 15;
            }
            else if (settings.get("SelectedNumOfWords").equals("3")) {
                lengthToHave = 20;
            }
            else if (settings.get("SelectedNumOfWords").equals("4")) {
                lengthToHave = 30;
            }
            int name = 1;
            Terms temp = new Terms();
            for (int i = 0; i < modifiedMaps.getSize(); i++) {
                String check = modifiedMaps.getDefinition(i);
                //System.out.println(i+1 + " length " + check.split(" ").length);
                if (check.split(" ").length >= lengthToHave) {
                    temp.addSet(String.valueOf(name), check);
                    name++;
                }
            }
            modifiedMaps = temp;
        }
    }

    public void guaranteeRandomModified() {
        if (modifiedMaps.getSize() > 0) {
            Random random = new Random();

            if (totalNumModified >= modifiedMaps.getSize()) {
                //System.out.println("inside");
                usedChallengesModified = new ArrayList<String>();
                totalNumModified = 0;
            }

            while (true) {
                currentModifiedString = modifiedMaps.getDefinition(random.nextInt(modifiedMaps.getSize()));
                //System.out.println("&&&&&" + currentModifiedString + "||||||||" + usedChallengesModified.toString());
                //System.out.println("Total num modified: " + totalNumModified);
                if (!usedChallengesModified.contains(currentModifiedString)) { // keep randomizing until a unique string appears
                    usedChallengesModified.add(currentModifiedString);
                    break;
                }
            }
            totalNumModified++;
        }
    }

    public String getModifiedString() {
        return currentModifiedString;
    }
    

    public static void main(String[] args) throws FileNotFoundException {
        //System.out.println(System.getProperty("user.dir"));
        Wordbank wordbank = new Wordbank();
        wordbank.readFile();
        Map<String,String> settings = new HashMap<String,String>();
        settings.put("Have Punctuation", "false");
        settings.put("Time/Word Mode", "word");
        settings.put("SelectedNumOfWords", "2");

        wordbank.updateModifiedMaps(settings);

        wordbank.modifiedMaps.printInOrder();
        System.out.println("================");
        
        for (int i = 0; i < 8; i++) {
            wordbank.guaranteeRandomModified();
            if (i==3) System.out.println();
            System.out.println(i+1 + " " + wordbank.getModifiedString());
            
        }



       // wordbank.maps.printInOrder();
        //System.out.println();

        //wordbank.modifiedMaps.printInOrder();
        /*
        
        // wordbank.randomizeString();
        System.out.println(wordbank.getCurrentString());
        for (int i = 0; i < 4; i ++) {
            wordbank.guaranteeRandom();
            System.out.println(wordbank.getCurrentString());
        }

         */
    }


}
