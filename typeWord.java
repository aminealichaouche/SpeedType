package SpeedType;
import java.util.ArrayList;

public class typeWord {
    
    private ArrayList<String> typedWords;
    private int index;
    public typeWord() {
        typedWords = new ArrayList<String>();
        index = -1;
    }

    

    public void addWord(String word) {
        if (word.equals(" ") || word.equals("")) return;
        index += 1;
        String newString = "";
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != ' ') {
                newString += word.charAt(i);
            }
        }
        typedWords.add(newString);
    }

    public void mergeArrays(String[] arr) {
        typedWords = new ArrayList<String>();
        for (String str: arr) {
            typedWords.add(str);
        }

    }

    /* 
    public void addWord(String word) {
        if (word.equals(" ") || word.equals("")) return;
        index += 1;
        String newString = "";


        for (char ch: word.toCharArray()) {
            if (ZCharacter.isLetter(ch) || ch == '?' || String.valueOf(ch).equals("'") || ch == '!') {
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) != ' ') {
                        newString += word.charAt(i);
                    }
                }
                typedWords.add(newString);
                break;
            } else {
                break;
            }
        }
        
    }
    */

    public String getWord() {
        if (typedWords.size() != 0)
            return (typedWords.get(index)); // to duplicate last letter to not delete it when pressing backspace
        else return "";
    }
    public String getWordAtIndex(int idx) {
        return typedWords.get(idx);
    }

    public int getIndex() {
        return index;
    }

    public int getSize() {
        return typedWords.size();
    }

    public void deleteWord() {
        index--;
        typedWords.remove(typedWords.size()-1);
    }

    public void printBank() {
        System.out.println(typedWords.toString());
    }

    public void reset() {
        typedWords = new ArrayList<String>();
    }

    public void checkArray() {
        for (int i = 0; i < typedWords.size(); i++) {
            for (char ch: typedWords.get(i).toCharArray()) {
                if (!Character.isLetter(ch) && ch != '?' && !String.valueOf(ch).equals("'") && ch != '!') {
                    typedWords.remove(i);
                }
            }
        }
    }
    

    public static void main(String[] args) {
        String ns = " Happy";
        String nw = "Fatty";
        String nl = " ";
        String np = "";
        typeWord wordbank = new typeWord();

        wordbank.addWord(ns);
        wordbank.addWord(nw);
        wordbank.addWord(nl);
        wordbank.addWord(np);
        //System.out.println(wordbank.typedWords.toString());
        typeWord wordBank2 = new typeWord();

        String string = "a;;;;;;;;;;;;;";
        String string2 = ";;;;;;;;;a";
        String string3 = "happy!";

        wordBank2.addWord(string);
        wordBank2.addWord(string2);
        wordBank2.addWord(string3);
        wordBank2.printBank();

    }

}
