package SpeedType;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.lang.Class.*;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// TODO add ability to continue stats from today (add to settings) FIXXXX THIS!!!!!!!!!!
// TODO wordbank
// TODO no punctuation


// TODO create settings file (loading from start)
// TODO FIX SAVES!!!



// TODO:
// TODO add enter as a keybind to do a fast reset. 
// TODO maybe make typedsofar only show the last 30 words?
// TODO Make menu look better
// TODO Dark mode on all frames

public class TestThatWorks {

    private static String Typing = "";
    private static ArrayList<JLabel> challengeArray;
    private static int index;
    private static int elapsedTime;
    private static int timeinSeconds;
    private static int timeinMilliseconds;
    private static int correctWordNum;
    private static int numOfTypedWords;
    private static JFrame game;
    private static SettingsFrame settingsFrame;
    private static StatsFrame statsFrame;
    private boolean isFirstTime = true;
    private static typeWord typedWords;
    private static boolean endReached = false;
    private static boolean settingsChanged = false;
    private static ArrayList<JPanel> AllPanels;
    private static ArrayList<JLabel> AllLabels;
    private static ArrayList<JButton> AllButtons;
    private static Player player;
    private static Player carriedPlayer;
    private static boolean playedGame = false;
    private static double wpmRaw;
    private static double wpmActual;
    private static double accuracy;
    private static Stat stats;
    private static Stat carriedStats;
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd-yy");
    private static Date currentDate = new Date();
    private static String challengeString;
    private static JPanel storyPanel;

    private static JButton restartButton = new JButton("Restart");

    //private static boolean alreadySavedCarried = false; // don't let user infinitely carry stats
    private static boolean timerStarted = true;

    private static double lastRaw;
    private static double lastActual;
    private static double lastAccuracy;

    private static Stat lastStat = new Stat();

    private static boolean validMapSize = true;
    private static boolean validTime = true;
    private static JLabel timeSoFar;
    private static JPanel infoPanel;

    private static int timeToEndAt = 0;
    private static JButton resetButton;

    private static Timer timer;
    private static Timer wpmLabelResetter;
    private static Timer backGroundChecker;

    public TestThatWorks() throws IOException {
        Typing = "";
        challengeArray = new ArrayList<JLabel>();
        index = -1;
        timeinSeconds = 0;
        correctWordNum = 0;
        numOfTypedWords = 0;
        typedWords = new typeWord();
        settingsFrame = new SettingsFrame();
        statsFrame = new StatsFrame();
        AllPanels = new ArrayList<JPanel>();
        AllLabels = new ArrayList<JLabel>();
        AllButtons = new ArrayList<JButton>();
        player = new Player();
        stats = new Stat();
        carriedStats = new Stat();
        carriedPlayer = new Player();

        if (StatsFrame.isTodaysStatPresent()) {
            File directory = new File(System.getProperty("user.dir") + "/SpeedType/Saves/" + "Save " + timeFormat.format(currentDate) + ".txt");
            Scanner scanner = new Scanner(directory);
            
            int gamesPlayed = 0;
            double avgRaw1 = 0;
            double avgActual1 = 0;
            double avgAccuracy1 = 0;

            while (scanner.hasNextLine()) {
                String currentLine = "";
                String[] tempCheck;
                currentLine = scanner.nextLine();
                tempCheck = currentLine.split(" ");
                if (currentLine.startsWith("Number of Games: ")) {
                    gamesPlayed = Integer.valueOf(tempCheck[tempCheck.length-1]);
                    //allTimeStats.addGameByAmount(Integer.valueOf(tempCheck[tempCheck.length-1]));
                }
                if (currentLine.startsWith("Average WPM (Raw): ")) {
                    avgRaw1 = Double.parseDouble(tempCheck[tempCheck.length-1]);
                    //allTimeStats.addRaw(Double.parseDouble(tempCheck[tempCheck.length-1])); 
                }
                if (currentLine.startsWith("Average WPM (Actual): ")) {
                    avgActual1 = Double.parseDouble(tempCheck[tempCheck.length-1]);
                    //allTimeStats.addActual(Double.parseDouble(tempCheck[tempCheck.length-1]));
                }
                if (currentLine.startsWith("Average Accuracy: ")) {
                    avgAccuracy1 = Double.parseDouble(tempCheck[tempCheck.length-1]);
                    //allTimeStats.addAccuracy(Double.parseDouble(tempCheck[tempCheck.length-1])); 
                }

                
                

            }
            for (int i = 0; i < gamesPlayed; i++) { // transfer number of games to carried player
                carriedPlayer.addGame();
            }

            carriedPlayer.addRaw(avgRaw1 * gamesPlayed);
            carriedPlayer.addActual(avgActual1 * gamesPlayed);
            carriedPlayer.addAccuracy(avgAccuracy1 * gamesPlayed);
            System.out.println("carried player games: " + carriedPlayer.getGames());
            scanner.close();

            carriedStats.setStats(carriedPlayer.getGames(), carriedPlayer.avgRaw(), carriedPlayer.avgActual(), carriedPlayer.avgAccuracy());
        }
    }

    public static void darkMode(String mode) {
        if (mode.equals("dark")) {
            for (JPanel panel: AllPanels) { // switch to dark mode
                panel.setBackground(Color.decode("#323437"));
            }
            for (JLabel label: AllLabels) {
                label.setForeground(Color.white);
            }
            for (JButton button: AllButtons) {
                button.setBackground(Color.gray);
                button.setForeground(Color.white);
                button.setBorderPainted(false);
            }
        } else { // switch to light mode
            for (JPanel panel: AllPanels) {
                panel.setBackground(Color.white);
            }
            for (JLabel label: AllLabels) {
                label.setForeground(Color.black);
            }
            for (JButton button: AllButtons) {
                button.setBackground(Color.white);
                button.setForeground(Color.black);
                button.setBorderPainted(true);
            }
        }
    }

    public static void gameScreen(JFrame frame, Map<String, String> settings) {
        game = new JFrame();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setSize(500,500);
        game.setVisible(true);
        game.setLayout(new GridLayout(8,1));
        game.setTitle("Game");
        game.setMinimumSize(new Dimension(900,700));
        game.setLocationRelativeTo(null);

        
        JPanel topPanel = new JPanel();
        storyPanel = new JPanel(); // what to type
        JPanel typedPanel = new JPanel(new FlowLayout()); // display what was typed so far
        infoPanel = new JPanel(new FlowLayout());
        JPanel textFieldPanel = new JPanel(); // box to type in
        JPanel bottomPanel = new JPanel(new GridLayout(4,3)); // return button

        AllPanels.add(topPanel);
        AllPanels.add(storyPanel);
        AllPanels.add(typedPanel);
        AllPanels.add(infoPanel);
        AllPanels.add(textFieldPanel);
        AllPanels.add(bottomPanel);
        
        JLabel challenge = new JLabel(); // redundant
        challengeString = "Have you tried writing this? The chicken man with a chicken face, likes eating his chicken sandwich with a happy face.";
        challengeString = "Have you tried writing this?";
        
        
        System.out.println("challengeString: " + challengeString);
        //challengeString = "Have you tried writing this? The chicken man with a chicken face, likes eating his chicken sandwich with a happy face. Chicken man chicken man chicken man chicken man chicken man chicken man";
        challenge.setText(challengeString);
        AllLabels.add(challenge);

        String[] prelim = challengeString.split(" ");
        for (String str: prelim) {
            JLabel colorString = new JLabel(str);
            challengeArray.add(colorString);
            AllLabels.add(colorString);
        }

        JLabel typedSoFar = new JLabel("You Typed: ");
        AllLabels.add(typedSoFar);

        JLabel wpm = new JLabel("WPM (Raw): " + numOfTypedWords + "     WPM (Actual): " + correctWordNum + "     Accuracy: 0%");
        timeSoFar = new JLabel("  Time: " + timeinSeconds);
        AllLabels.add(wpm);
        AllLabels.add(timeSoFar);

        timer = new Timer(100, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                elapsedTime += 10;
                timeinSeconds = (elapsedTime/100); 
                timeinMilliseconds = (elapsedTime/10) % 10;
                //System.out.println(elapsedTime);
                // timeSoFar.setText("  Time: " + timeinSeconds + "." + timeinMilliseconds);
                whatTimeToUse();
                
                
                
            }
            
        });
        
        wpmLabelResetter = new Timer(10, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                accuracy = 0;
                if (numOfTypedWords > 0) {
                    accuracy = Math.round( (correctWordNum/ (numOfTypedWords+ 0.0)) * 1000.0 ) / 10.0;
                }
                wpmRaw = Math.round((numOfTypedWords / ((timeinSeconds + (timeinMilliseconds/10.0)) / 60.0)) * 10.0) / 10.0;
                wpmActual = Math.round((correctWordNum / ((timeinSeconds + (timeinMilliseconds/10.0)) / 60.0)) * 10.0) / 10.0;
                
                wpm.setText("WPM (Raw): " + wpmRaw + "     WPM (Actual): " + wpmActual + "     Accuracy: " + accuracy + "%"); // fix this to not have roundig numbers
                
                
            }
            
        });
        
        backGroundChecker = new Timer(100, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeinSeconds == timeToEndAt && statsFrame.getMapSettingsFrame().getSettings().get("Time/Word Mode").equals("time")) {
                    timer.stop();
                    wpmLabelResetter.stop();
                    endReached = true;
                    resetButton.setVisible(true);
                    whenUserReachesEnd(); // save stats like what happens when you reach the end of challengeArray

                } else if (statsFrame.getMapSettingsFrame().getSettings().get("Apply Settings").equals("false") && timeinSeconds == timeToEndAt)  {// default settings 
                    timer.stop();
                    wpmLabelResetter.stop();
                    endReached = true;
                    resetButton.setVisible(true);
                    whenUserReachesEnd(); // save stats like what happens when you reach the end of challengeArray
                }
                System.out.println(timeinSeconds + "  " + timeinMilliseconds);
            }
            
        });

        JTextField userInput = new JTextField();
        userInput.setPreferredSize(new Dimension(200,35));
        userInput.setFont(new Font("Calibri", Font.BOLD, 30));

        JButton returnButton = new JButton("Return");
        AllButtons.add(returnButton);

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setVisible(false);
                frame.setVisible(true);
                typedWords.printBank();
                Typing = "";
                settingsChanged = false; // allow for setting changes when coming back
                playedGame = false;
                System.out.println("Played Games: " + player.getGames());
            }
        });

        restartButton.setVisible(false);
        AllButtons.add(restartButton);

        resetButton = new JButton("Reset");
        resetButton.setVisible(false);
        AllButtons.add(resetButton);

        restartButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                player.addGameByAmount(-1);
                carriedPlayer.addGameByAmount(-1);

                // wordbank reset
                typedWords = new typeWord();
                Typing = "";
                typedSoFar.setText(Typing);
                index = -1;

                // timer reset
                timer.stop();
                backGroundChecker.stop();
                elapsedTime = 0;
                timeinMilliseconds = 0;
                timeinSeconds = 0;
                whatTimeToUse();
                userInput.setText("");
                 

                // wpm reset
                numOfTypedWords = 0;
                correctWordNum = 0;
                wpmRaw = 0;
                wpmActual = 0;
                accuracy = 0;
                wpm.setText("WPM (Raw): " + 0 + "     WPM (Actual): " + 0 + "     Accuracy: " + 0 + "%");

                // reset Typing words
                for (JLabel label: challengeArray) {
                    if (settings.get("dark/light mode").equals("dark"))    
                    label.setForeground(Color.white);
                    else
                    label.setForeground(Color.black);
                }
                endReached = false;
                playedGame = true;
                timerStarted = true;
                // subtract last game's raw/actual/accuracy
                if (player.getRaw() != 0) {
                    player.addRaw(-lastRaw);
                    carriedPlayer.addRaw(-lastRaw);
                }
                if (player.getActual() != 0) {
                    player.addActual(-lastActual);
                    carriedPlayer.addActual(-lastActual);
                }
                if (player.getAccuracy() != 0) {
                    player.addAccuracy(-lastAccuracy);
                    carriedPlayer.addAccuracy(-lastAccuracy);
                }
                
                stats.setStats(player.getGames(), player.avgRaw(), player.avgActual(), player.avgAccuracy());
                carriedStats.setStats(carriedPlayer.getGames(), carriedPlayer.avgRaw(), carriedPlayer.avgActual(), carriedPlayer.avgAccuracy());

                resetButton.setVisible(false);
                restartButton.setVisible(false);
            }
        });

        resetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // wordbank reset
                typedWords = new typeWord();
                Typing = "";
                typedSoFar.setText(Typing);
                index = -1;

                // timer reset
                timer.stop();
                backGroundChecker.stop();
                elapsedTime = 0;
                timeinMilliseconds = 0;
                timeinSeconds = 0;
                whatTimeToUse();
                userInput.setText("");
                 

                // wpm reset
                numOfTypedWords = 0;
                correctWordNum = 0;
                wpmRaw = 0;
                wpmActual = 0;
                accuracy = 0;
                wpm.setText("WPM (Raw): " + 0 + "     WPM (Actual): " + 0 + "     Accuracy: " + 0 + "%");

                

                // get next map
                /* 
                statsFrame.getWordbank().guaranteeRandom();
                challengeString = statsFrame.getWordbank().getCurrentString();
                */
                challengeString = getMap();
                String[] prelim = challengeString.split(" ");
                challengeArray = new ArrayList<JLabel>();
                for (String str: prelim) {
                    JLabel colorString = new JLabel(str);
                    challengeArray.add(colorString);
                    AllLabels.add(colorString);
                }
                for (Component component: storyPanel.getComponents()) {
                    storyPanel.remove(component);
                }
                storyPanel.validate();
                storyPanel.repaint();

                for (JLabel label: challengeArray) {
                    storyPanel.add(label);
                }

                // reset color for challenge words
                for (JLabel label: challengeArray) {
                    if (settings.get("dark/light mode").equals("dark"))    
                    label.setForeground(Color.white);
                    else
                    label.setForeground(Color.black);
                }
                endReached = false;
                playedGame = false;
                timerStarted = true;
                resetButton.setVisible(false);
                //restartButton.setVisible(true);


                frame.validate();
                frame.repaint();
            }
        });
        
        userInput.addKeyListener(new KeyListener() {

              
            @Override
            public void keyPressed(KeyEvent event) {
                if (!validMapSize || !validTime) {
                    return;
                }


                if (!endReached) {

                    System.out.println(typedWords.getSize() + " " + Typing.split(" ").length );
                    if (event.getKeyCode() == 8 && typedWords.getSize() < Typing.split(" ").length && Typing.length() > 1) { 

                        String ns = "";
                        for (int i = 0; i < Typing.length()-1; i++) {
                            ns += Typing.charAt(i);
                        }
                        Typing = ns;
                    }
                    else if ((event.getKeyCode() != 8 && event.getKeyCode() != 32 && event.getKeyCode() != 16 && event.getKeyCode() != 157 && event.getKeyCode() != 18 && event.getKeyCode() != 17 && event.getKeyCode() != 524 && event.getKeyChar() != '☺'/*&& Character.isLetter(event.getKeyChar())*/)) { // add letter if it is a letter
                        timer.start();
                        backGroundChecker.start();
                        wpmLabelResetter.start();
                        //timerStarted = true;
                        restartButton.setVisible(true);
                        // add playedGame each time timer starts
                        /* 
                        if (!playedGame) {
                            player.addGame();
                            carriedPlayer.addGame();
                            resetButton.setVisible(false);
                            //restartButton.setVisible(true);
                            playedGame = true;
                        }
                        //player.addGame();
                        */
                        if (timerStarted) {
                            player.addGame();
                            carriedPlayer.addGame();
                            restartButton.setVisible(true);
                            timerStarted = false;
                        }
                        
                        Typing += event.getKeyChar();
                    } else if (event.getKeyCode() == 32 && (typedWords.getSize() < challengeArray.size())) { // when user clicks space, reset textfield
                        Typing += event.getKeyChar();
                        if (!userInput.getText().equals("") && !userInput.getText().equals(" " ) && !userInput.getText().equals("  ")) {
                            index++;
                            typedWords.addWord(userInput.getText());
                        }
                        System.out.println(index);
                        typedWords.printBank();
    
                        // if user reaches end of challengeArray;
                        if (index == challengeArray.size()-1) {
                            timer.stop();
                            backGroundChecker.stop();
                            wpmLabelResetter.stop();
                            endReached = true;
                            resetButton.setVisible(true);
                
                            /* 
                            if (timerStarted) { // add game for restarted game.
                                System.out.println("We have entered");
                                player.addGame();
                                carriedPlayer.addGame();
                            }
                            */
                            
                            //alreadySavedCarried = false; // allow carry save to happen
                
                            player.addRaw(wpmRaw);
                            carriedPlayer.addRaw(wpmRaw);
                            lastRaw = wpmRaw;
                            System.out.println("Raw: " + wpmRaw);
                            player.addActual(wpmActual);
                            carriedPlayer.addActual(wpmActual);
                            lastActual = wpmActual;
                            System.out.println("Actual: " + wpmActual);
                            player.addAccuracy(accuracy);
                            carriedPlayer.addAccuracy(accuracy);
                            lastAccuracy = accuracy;
                            System.out.println("Accuracy: " + accuracy);
                
                            stats.setStats(player.getGames(), player.avgRaw(), player.avgActual(), player.avgAccuracy());
                
                            System.out.println("GAMES:" + carriedPlayer.getGames());
                            carriedStats.setStats(carriedPlayer.getGames(), carriedPlayer.avgRaw(), carriedPlayer.avgActual(), carriedPlayer.avgAccuracy());
                            
                            lastStat.setStats(1, lastRaw, lastActual, lastAccuracy);
                
                            System.out.println("wpm Raw: " + player.avgRaw() + " wpm Actual: " + player.avgActual() + " accuracy: " + player.avgAccuracy());
                            
                        }
    
                        if (userInput.getText().length() > 0 && !userInput.getText().equals(" ") && !userInput.getText().equals("  ")) {
                            if (index < challengeArray.size() && typedWords.getWordAtIndex(index).equals(challengeArray.get(index).getText())) { // check if typed word equals the challenge string
                                challengeArray.get(index).setForeground(Color.green);
                                correctWordNum++;
                        }
                        numOfTypedWords++;
                    }
                    userInput.setText("");
                    } else if (event.getKeyChar() == '?' && event.getKeyCode() != 47) {
    
                    } else if (event.getKeyCode() == 8 && Typing.length() > 0 && Typing.charAt(Typing.length()-1) == ' ') { // delete extra space
                        String ns = "";
                        for (int i = 0; i < Typing.length()-1; i++) {
                            ns += Typing.charAt(i);
                        }
                        Typing = ns;
                    } else if (event.getKeyCode() == 8 && userInput.getText().equals("") && Typing.length() != 0 && index != -1) { // replace textfield with word when you click backspace
                        typedWords.printBank();
                        System.out.println(index);
                        if (index < challengeArray.size() && !(index <= -1)) {
                            if (challengeArray.get(index).getForeground().equals(Color.green)) {
                                System.out.println("was correct");
                                correctWordNum--;
                            }
                            else {
                                System.out.println("was black");
                            }
                            numOfTypedWords--;
                            if (settings.get("dark/light mode").equals("light"))
                            challengeArray.get(index).setForeground(Color.black); // switch back
                            else
                            challengeArray.get(index).setForeground(Color.white);
                            
                        }
    
                        index--;
                        if (typedWords.getSize() != 0) {
                            userInput.setText(typedWords.getWord() + typedWords.getWord().charAt(typedWords.getWord().length()-1));
                            typedWords.deleteWord();
                        }
                    } else if (event.getKeyCode() == 8 && Typing.length() > 0) { // delete tracking jLabel
                        String ns = "";
                        for (int i = 0; i < Typing.length()-1; i++) {
                            ns += Typing.charAt(i);
                        }
                        Typing = ns;
                    } else if (event.getKeyChar() == '?' || event.getKeyChar() == '!' || String.valueOf(event.getKeyChar()).equals("'")) {
                        Typing += event.getKeyChar();
                    } else {

                    }
                
                typedSoFar.setText("<html>" + Typing + "</html>");
    
                }
                }
                

                // System.out.println(event.getKeyChar() + "Code equals: " + event.getKeyCode());
                // System.out.println("Type: ");
                
                


            @Override
            public void keyReleased(KeyEvent event) {
                if (userInput.getText().length() > Typing.length()) {
                    String ns = "";
                    for (int i = 0; i < userInput.getText().length()-1; i++) {
                        ns += userInput.getText().charAt(i);
                    }
                    userInput.setText(ns);
                }
                if (endReached) {
                    userInput.setText("");
                }
            }
            @Override
            public void keyTyped(KeyEvent event) {
                
            }});

        for (JLabel label: challengeArray) {
            storyPanel.add(label);
        }
        
        typedPanel.add(typedSoFar);

        infoPanel.add(wpm);
        infoPanel.add(timeSoFar);
        
        textFieldPanel.add(userInput);

        // allows for dark/light mode to change
        JPanel bottomPanelBuffer1 = new JPanel();
        JPanel bottomPanelBuffer2 = new JPanel();
        JPanel bottomPanelBuffer3 = new JPanel();
        JPanel bottomPanelBuffer4 = new JPanel();
        JPanel bottomPanelBuffer6 = new JPanel();
        JPanel bottomPanelBuffer7 = new JPanel();
        JPanel bottomPanelBuffer8 = new JPanel();
        JPanel bottomPanelBuffer9 = new JPanel();
        JPanel bottomPanelBuffer10 = new JPanel();

        AllPanels.add(bottomPanelBuffer1);
        AllPanels.add(bottomPanelBuffer2);
        AllPanels.add(bottomPanelBuffer3);
        AllPanels.add(bottomPanelBuffer4);
        AllPanels.add(bottomPanelBuffer6);
        AllPanels.add(bottomPanelBuffer7);
        AllPanels.add(bottomPanelBuffer8);
        AllPanels.add(bottomPanelBuffer9);
        AllPanels.add(bottomPanelBuffer10);

        bottomPanel.add(bottomPanelBuffer1);
        bottomPanel.add(bottomPanelBuffer2);
        bottomPanel.add(bottomPanelBuffer3);
        bottomPanel.add(bottomPanelBuffer4);
        bottomPanel.add(restartButton);
        bottomPanel.add(bottomPanelBuffer6);
        bottomPanel.add(bottomPanelBuffer7);
        bottomPanel.add(resetButton);
        bottomPanel.add(bottomPanelBuffer9);
        bottomPanel.add(bottomPanelBuffer10);
        bottomPanel.add(returnButton);

        JPanel gamePanelBuffer4 = new JPanel();
        JPanel gamePanelBuffer6 = new JPanel();

        AllPanels.add(gamePanelBuffer4);
        AllPanels.add(gamePanelBuffer6);

        game.add(topPanel);
        game.add(storyPanel);
        game.add(typedPanel);
        game.add(gamePanelBuffer4);
        game.add(infoPanel); // add stopwatch and WPM here 
        game.add(gamePanelBuffer6);
        game.add(textFieldPanel);
        game.add(bottomPanel);
        

        };
        
    public void mainMenu() {
        JFrame frame = new JFrame();
        frame.setSize(700,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(7,1));
        frame.setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(Color.black);

        JLabel title = new JLabel("SpeedType!");
        title.setForeground(Color.white);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.darkGray);

        JPanel buttonPanel = new JPanel();
         
        JButton play = new JButton("Play");
        play.setPreferredSize(new Dimension(85, 40));
        JButton exit = new JButton("Exit");
        exit.setPreferredSize(new Dimension(85,40));
        JButton settingsButton = new JButton("Settings");
        settingsButton.setPreferredSize(new Dimension(85,40));
        JButton statsLoadButton = new JButton("Stats/Load");
        statsLoadButton.setPreferredSize(new Dimension(85,40));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        settingsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                settingsFrame.getFrame().setVisible(true);
                frame.setVisible(false);
            }
            
        });

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                if (isFirstTime) {
                    gameScreen(frame, settingsFrame.getSettings());
                    isFirstTime = false;
                } else {
                    game.setVisible(true);
                }

                if (!settingsChanged) {
                    settingsChanged = true;
                    if (settingsFrame.getSettings().get("dark/light mode").equals("dark")) {
                        darkMode("dark");
                    } else { // switch to light mode
                        darkMode("light");
                    }
                }

                challengeString = getMap();
                System.out.println(challengeString);
                String[] prelim = challengeString.split(" ");
                challengeArray = new ArrayList<JLabel>();
                for (String str: prelim) {
                    JLabel colorString = new JLabel(str);
                    challengeArray.add(colorString);
                    AllLabels.add(colorString);
                }
                for (Component component: storyPanel.getComponents()) {
                    storyPanel.remove(component);
                }
                

                for (JLabel label: challengeArray) {
                    storyPanel.add(label);
                }

                for (JLabel label: challengeArray) {
                    if (settingsFrame.getSettings().get("dark/light mode").equals("dark"))    
                    label.setForeground(Color.white);
                    else
                    label.setForeground(Color.black);
                }

                storyPanel.validate();
                storyPanel.repaint();


                // auto set timer when play is clicked based on settings
                // apply settings and default setting is 10 seconds
                whatTimeToUse();
                

                infoPanel.validate();
                infoPanel.repaint();
            }
        });

        statsLoadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);

                // write stats to JLabels
                statsFrame.getNumOfGamesLabel().setText("Number of Games: " + String.valueOf(player.getGames()));
                statsFrame.getAvgRawLabel().setText("Average WPM (Raw): " + String.valueOf(player.avgRaw()));
                statsFrame.getAvgActualLabel().setText("Average WPM (Actual): " + String.valueOf(player.avgActual()));
                statsFrame.getAvgAccuracyLabel().setText("Average Accuracy: " + String.valueOf(player.avgAccuracy()) + "%");

                statsFrame.getFrame().setVisible(true);

                try {
                    StatsFrame.updateDashboard(settingsFrame.getSettings(), carriedPlayer);
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                statsFrame.loadSettingsOntoMapSettingsFrame(settingsFrame.getSettings());
                statsFrame.darkMode(settingsFrame.getSettings().get("dark/light mode").equals("dark"));

            }

            
        });
        
        buttonPanel.add(play);
        buttonPanel.add(statsLoadButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(exit);
        topPanel.add(title);

        frame.add(topPanel);
        frame.add(new JPanel());
        frame.add(new JPanel());
        frame.add(buttonPanel);
        frame.add(new JPanel());
        frame.add(new JPanel());
        frame.add(bottomPanel);

        frame.setVisible(true);

        // statsLoad menu implementation
        statsFrame.getScrollToTheTopButtonForSaves().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                statsFrame.scrollToTopForSaves();
            }
            
        });

        statsFrame.getScrollToTheBottomButtonForSaves().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                statsFrame.scrollToBottomForSaves();
            }
            
        });

        statsFrame.getScrollUpButtonForSaves().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                statsFrame.scrollUpForSaves();
            }
            
        });

        statsFrame.getScrollDownButtonForSaves().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                statsFrame.scrollDownForSaves();
            }
            
        });

        

        statsFrame.getReturnButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
                statsFrame.getFrame().setVisible(false);
            }
            
        });

        statsFrame.getSaveButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                restartButton.setVisible(false);
                
                if (settingsFrame.getSettings().get("carry save").equals("false")) {
                    System.out.println("we are not carrying");
                    try {
                        stats.makeSave();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        // TODO no need to run test, just modify the last button!
                        StatsFrame.addTodaysStats();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                } else { // carry stats from today's stats

                try {
                    carriedStats.makeSave();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } 
                try {
                    StatsFrame.addTodaysStats();
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                //alreadySavedCarried = true;
                }
            }
            
        });

        statsFrame.getScrollUpButtonForMaps().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                StatsFrame.scrollUpForMaps();
                
            }
            
        });

        statsFrame.getScrollDownButtonForMaps().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                StatsFrame.scrollDownForMaps();
                
            }
            
        });

        statsFrame.getPreviewButtonForMaps().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                StatsFrame.preview();
            }
            
        });

        // miscButtons implementation

        statsFrame.getSeeLastButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                statsFrame.showSpecificStats(lastStat);
            }
            
        });

        statsFrame.getSeeCurrentButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                statsFrame.showSpecificStats(stats);
            }
            
        });

        statsFrame.getSeeTodayButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                statsFrame.showSpecificStats(carriedStats);
            }
            
        });

        // setting menu implementation
        settingsFrame.getReturnButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
                settingsFrame.getFrame().setVisible(false);
            }
            
        });


    }

    public static String getMap() {
        System.out.println("SelectedMapIndexTestThatWorks: " + statsFrame.getSelectedMapIndex());

        if (statsFrame.getWordbank().getModifiedMaps().getSize() == 0 || statsFrame.getWordbank().getMaps().getSize() == 0) { // if there are no maps
            //System.out.println("enter 1");
            validMapSize = false;
            return "Check Map Settings/ Filters (No Map Found)";
        }

        if (statsFrame.getMapSettingsFrame().getSettings().get("Apply Settings").equals("true")) { // use applied settings
            //System.out.println("enter 2");
            if (statsFrame.getMapSettingsFrame().getSettings().get("Randomize Maps").equals("false")) {
                if (statsFrame.getSelectedMapIndex() == -1) { // if there is no map selected
                    return "Check Map Settings/ Filters (No Map Found)";
                }
                
                
                validMapSize = true;
                return statsFrame.getWordbank().getModifiedMaps().getDefinition(statsFrame.getSelectedMapIndex());
            }

            //System.out.println("goes here 2");
            statsFrame.getWordbank().guaranteeRandomModified(); // there is a bug in code
            //System.out.println("goes here 3");
            validMapSize = true;
            return statsFrame.getWordbank().getModifiedString();
        } else {
            //System.out.println("enter 3");
            statsFrame.getWordbank().guaranteeRandom();
            validMapSize = true;
            return statsFrame.getWordbank().getCurrentString();
        }
        
    }

    public static void whatTimeToUse() {
        if (statsFrame.getMapSettingsFrame().isApplySettingsTrue() && statsFrame.getMapSettingsFrame().getSettings().get("Time/Word Mode").equals("time")) {
            //System.out.println("inside");
            if (statsFrame.getMapSettingsFrame().getSettings().get("SelectedNumOfWords").equals("1")) {
                double temp = (10 - (timeinSeconds + (timeinMilliseconds/10.0)));
                timeSoFar.setText("  Time: " + String.format("%.1f", temp));
                timeToEndAt = 10;
            } else if (statsFrame.getMapSettingsFrame().getSettings().get("SelectedNumOfWords").equals("2")) {
                double temp = (15 - (timeinSeconds + (timeinMilliseconds/10.0)));
                timeSoFar.setText("  Time: " + String.format("%.1f", temp));
                timeToEndAt = 15;
            } else if (statsFrame.getMapSettingsFrame().getSettings().get("SelectedNumOfWords").equals("3")) {
                double temp = (20 - (timeinSeconds + (timeinMilliseconds/10.0)));
                timeSoFar.setText("  Time: " + String.format("%.1f", temp));
                timeToEndAt = 20;
            } else if (statsFrame.getMapSettingsFrame().getSettings().get("SelectedNumOfWords").equals("4")) {
                double temp = (30 - (timeinSeconds + (timeinMilliseconds/10.0)));
                timeSoFar.setText("  Time: " + String.format("%.1f", temp));
                timeToEndAt = 30;
            } else if (statsFrame.getMapSettingsFrame().getSettings().get("SelectedNumOfWords").equals("-1")) {
                challengeString = "Recheck time setting";
                // write message to challengeArray
                String[] prelim = challengeString.split(" ");
                challengeArray = new ArrayList<JLabel>();
                for (String str: prelim) {
                    JLabel colorString = new JLabel(str);
                    challengeArray.add(colorString);
                    AllLabels.add(colorString);
                }
                for (Component component: storyPanel.getComponents()) {
                    storyPanel.remove(component);
                }
                

                for (JLabel label: challengeArray) {
                    storyPanel.add(label);
                }

                // reset color for challenge words
                for (JLabel label: challengeArray) {
                    if (settingsFrame.getSettings().get("dark/light mode").equals("dark"))    
                    label.setForeground(Color.white);
                    else
                    label.setForeground(Color.black);
                }

                storyPanel.validate();
                storyPanel.repaint();

                timeSoFar.setText("  Time: error");
                validTime = false;
                return;
            }
        } else if (!statsFrame.getMapSettingsFrame().isApplySettingsTrue()) { // default setting
            double temp = (10 - (timeinSeconds + (timeinMilliseconds/10.0)));
            timeSoFar.setText("  Time: " + String.format("%.1f", temp));

            timeToEndAt = 10;
        } else {
            timeSoFar.setText("  Time: " + timeinSeconds + "." + timeinMilliseconds);
        }
        validTime = true;
    }

    public static void whenUserReachesEnd() {
        timer.stop();
        backGroundChecker.stop();
        wpmLabelResetter.stop();
        endReached = true;
        resetButton.setVisible(true);

        /* 
        if (timerStarted) { // add game for restarted game.
            System.out.println("We have entered");
            player.addGame();
            carriedPlayer.addGame();
        }
        */
        
        //alreadySavedCarried = false; // allow carry save to happen

        player.addRaw(wpmRaw);
        carriedPlayer.addRaw(wpmRaw);
        lastRaw = wpmRaw;
        System.out.println("Raw: " + wpmRaw);
        player.addActual(wpmActual);
        carriedPlayer.addActual(wpmActual);
        lastActual = wpmActual;
        System.out.println("Actual: " + wpmActual);
        player.addAccuracy(accuracy);
        carriedPlayer.addAccuracy(accuracy);
        lastAccuracy = accuracy;
        System.out.println("Accuracy: " + accuracy);

        stats.setStats(player.getGames(), player.avgRaw(), player.avgActual(), player.avgAccuracy());

        System.out.println("GAMES:" + carriedPlayer.getGames());
        carriedStats.setStats(carriedPlayer.getGames(), carriedPlayer.avgRaw(), carriedPlayer.avgActual(), carriedPlayer.avgAccuracy());
        
        lastStat.setStats(1, lastRaw, lastActual, lastAccuracy);

        System.out.println("wpm Raw: " + player.avgRaw() + " wpm Actual: " + player.avgActual() + " accuracy: " + player.avgAccuracy());
            
        
    }

    public static void main(String[] args) throws IOException {
        TestThatWorks driver = new TestThatWorks();
        driver.mainMenu();
        
    }

    
}
