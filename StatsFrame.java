package SpeedType;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.Pipe;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// TODO fix the save button when it is today but it was not made yet. (I think it should be working now)
// TODO change pickstatbuttons when we change shownbuttons as well (especially when you modify the last button of shownbuttons)

public class StatsFrame {
    
    private static JFrame frame;
    private static GridBagConstraints gbc;
    private JButton returnButton;
    private JButton saveButton;
    private static Date currentDate;
    private static SimpleDateFormat timeFormat;
    private static ArrayList<JPanel> allPanels;
    private static ArrayList<JButton> allButtons;
    private static ArrayList<JLabel> allLabels;
    private static ArrayList<mJButton> pickStatButtons;
    private static ArrayList<mJButton> pickMapButtons;
    private static Player allTimeStats;
    private JPanel pickStatPanel;
    private JPanel pickMapPanel;
    private static ArrayList<JPanel> panelSlots;
    private static ArrayList<JPanel> panelSlotsForMaps;
    private static int indexSlot;
    private static int indexSlotForMaps;
    private static ArrayList<mJButton> shownButtons;
    private static ArrayList<mJButton> shownButtonsForMaps;
    private static int name = 0;
    private static int nameForMaps = 0;
    private static JButton scrollUp;
    private static int indexForSaves = 0;
    private static int indexForMaps = 0;
    private static JButton scrollDown;
    private static boolean isFirstTime = true;
    private static boolean isFirstTimeForMaps = true;
    private static JButton scrollToTopForSaves;
    private static JButton scrollToBottomForSaves;
    private static JButton seeLastButton;
    private static JButton seeCurrentButton;
    private static JButton seeTodayButton;
    private static int selectedMapIndex = -1;
    private static MapSettingsFrame mapSettingsFrame;

    private static JButton scrollUpButtonForMaps;
    private static JButton scrollDownButtonForMaps;
    private static JButton settingsButtonForMaps;
    private static JTextField searchBar;
    private static JButton previewButton;
    private static JLabel statusMessage;

    private static JLabel avgAccuracy;
    private static JLabel avgActual;
    private static JLabel avgRaw;
    private static JLabel numOfGames;

    private static int gamesPlayed = 0;
    private static double avgRaw1 = 0;
    private static double avgActual1 = 0;
    private static double avgAccuracy1 = 0;
    private static int daysPlayed = 0;

    private static Wordbank wordBank;
    private static String userInput;

    private static boolean isInDarkMode = false;

    public StatsFrame() throws IOException {
        currentDate = new Date();
        timeFormat = new SimpleDateFormat("MM-dd-yy");
        allPanels = new ArrayList<JPanel>();
        allButtons = new ArrayList<JButton>();
        allLabels = new ArrayList<JLabel>();
        pickStatButtons = new ArrayList<mJButton>();
        pickMapButtons = new ArrayList<mJButton>();
        allTimeStats = new Player();
        panelSlots = new ArrayList<JPanel>();
        panelSlotsForMaps = new ArrayList<JPanel>();
        shownButtons = new ArrayList<mJButton>();
        shownButtonsForMaps = new ArrayList<mJButton>();
        wordBank = new Wordbank();
        wordBank.readFile();
        mapSettingsFrame = new MapSettingsFrame();
        System.out.println("Beginning: " + mapSettingsFrame.getSettings().get("SelectedNumOfWords"));
        System.out.println(mapSettingsFrame.getSelectedNumOfWords());

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 700);
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(750,700));

        JPanel framePanel = new JPanel(new BorderLayout());
        framePanel.setSize(frame.getWidth(), frame.getHeight());
        allPanels.add(framePanel);

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(frame.getWidth(), 100));
        topPanel.setBackground(Color.black);
        // allPanels.add(topPanel);

        // start of middle panels

        JPanel middleCenterPanel = new JPanel(new BorderLayout());
        middleCenterPanel.setPreferredSize(new Dimension(frame.getWidth(), 500));
        middleCenterPanel.setBackground(Color.white);
        allPanels.add(middleCenterPanel);

        JPanel middleLeftPanel = new JPanel(new BorderLayout());
        middleLeftPanel.setPreferredSize(new Dimension(200, 500));
        middleLeftPanel.setBackground(Color.white);
        allPanels.add(middleLeftPanel);

        JPanel leftTextBar = new JPanel();
        leftTextBar.setPreferredSize(new Dimension(200,30));
        leftTextBar.setBackground(Color.CYAN);
        allPanels.add(leftTextBar);
        
        JLabel pickStats = new JLabel("Choose a Stat");
        leftTextBar.add(pickStats);
        allLabels.add(pickStats);

        pickStatPanel = new JPanel(new GridLayout(10,1));
        pickStatPanel.setPreferredSize(new Dimension(200, middleLeftPanel.getHeight()-leftTextBar.getHeight()));
        pickStatPanel.setBackground(Color.darkGray);
        allPanels.add(pickStatPanel);

        // middleMiddlePanel displays Stats and other buttons

        JPanel middleMiddlePanel = new JPanel(new GridLayout(13,1));
        allPanels.add(middleMiddlePanel);
        // middleMiddlePanel.setBackground(Color.white);

        JPanel panel1 = new JPanel();

        JPanel numOfGamesPanel = new JPanel();
        numOfGames = new JLabel("Number Of Games: " + 10);
        numOfGamesPanel.add(numOfGames);
        allLabels.add(numOfGames);

        JPanel avgRawPanel = new JPanel();
        avgRaw = new JLabel("Average WPM (Raw): " + 80.0);
        avgRawPanel.add(avgRaw);
        allLabels.add(avgRaw);

        JPanel avgActualPanel = new JPanel();
        avgActual = new JLabel("Average WPM (Actual): " + 75.0);
        avgActualPanel.add(avgActual);
        allLabels.add(avgActual);

        JPanel avgAccuracyPanel = new JPanel();
        avgAccuracy = new JLabel("Average Accuracy: " + 100 + "%");
        avgAccuracyPanel.add(avgAccuracy);
        allLabels.add(avgAccuracy);

        JPanel miscButtonsPanel = new JPanel(new GridLayout(1, 3));

        JPanel seeLastGridPanel1 = new JPanel();
        JPanel seeCurrentGridPanel2 = new JPanel();
        JPanel seeTodayGridPanel3 = new JPanel();

        miscButtonsPanel.add(seeLastGridPanel1);
        miscButtonsPanel.add(seeCurrentGridPanel2);
        miscButtonsPanel.add(seeTodayGridPanel3);

        allPanels.add(seeLastGridPanel1);
        allPanels.add(seeCurrentGridPanel2);
        allPanels.add(seeTodayGridPanel3);

        JPanel panel5_5 = new JPanel();
        JPanel panel7 = new JPanel();
        JPanel scrollToTopPanel = new JPanel(new GridLayout(1,3));
        JPanel scrollToBottomPanel = new JPanel(new GridLayout(1,3));
        JPanel scrollUpPanel = new JPanel(new GridLayout(1,3));
        JPanel scrollDownPanel = new JPanel(new GridLayout(1,3));
        JPanel saveButtonPanel = new JPanel(new GridLayout(1,3));

        JPanel scrollToTopPanelGridPanel1 = new JPanel();
        JPanel scrollToTopPanelGridPanel2 = new JPanel();
        JPanel scrollToTopPanelGridPanel3 = new JPanel();

        scrollToTopPanel.add(scrollToTopPanelGridPanel1);
        scrollToTopPanel.add(scrollToTopPanelGridPanel2);
        scrollToTopPanel.add(scrollToTopPanelGridPanel3);

        JPanel scrollToBottomPanelGridPanel1 = new JPanel();
        JPanel scrollToBottomPanelGridPanel2 = new JPanel(new BorderLayout());
        JPanel scrollToBottomPanelGridPanel3 = new JPanel();

        scrollToBottomPanel.add(scrollToBottomPanelGridPanel1);
        scrollToBottomPanel.add(scrollToBottomPanelGridPanel2);
        scrollToBottomPanel.add(scrollToBottomPanelGridPanel3);

        JPanel scrollToBottomPanelGridPanel2GridPanel1 = new JPanel();
        JPanel scrollToBottomPanelGridPanel2GridPanel2 = new JPanel(new BorderLayout());

        //scrollToBottomPanelGridPanel2.add(scrollToBottomPanelGridPanel2GridPanel1);
        scrollToBottomPanelGridPanel2.add(scrollToBottomPanelGridPanel2GridPanel2);

        allPanels.add(scrollToBottomPanelGridPanel2GridPanel1);
        allPanels.add(scrollToBottomPanelGridPanel2GridPanel2);

        JPanel scrollUpPanelGridPanel1 = new JPanel();
        JPanel scrollUpPanelGridPanel2 = new JPanel();
        JPanel scrollUpPanelGridPanel3 = new JPanel();

        scrollUpPanel.add(scrollUpPanelGridPanel1);
        scrollUpPanel.add(scrollUpPanelGridPanel2);
        scrollUpPanel.add(scrollUpPanelGridPanel3);

        JPanel scrollDownPanelGridPanel1 = new JPanel();
        JPanel scrollDownPanelGridPanel2 = new JPanel();
        JPanel scrollDownPanelGridPanel3 = new JPanel();

        scrollDownPanel.add(scrollDownPanelGridPanel1);
        scrollDownPanel.add(scrollDownPanelGridPanel2);
        scrollDownPanel.add(scrollDownPanelGridPanel3);

        JPanel saveButtonPanelGridPanel1 = new JPanel();
        JPanel saveButtonPanelGridPanel2 = new JPanel();
        JPanel saveButtonPanelGridPanel3 = new JPanel();

        saveButtonPanel.add(saveButtonPanelGridPanel1);
        saveButtonPanel.add(saveButtonPanelGridPanel2);
        saveButtonPanel.add(saveButtonPanelGridPanel3);



        allPanels.add(panel1);
        allPanels.add(numOfGamesPanel);
        allPanels.add(avgRawPanel);
        allPanels.add(avgActualPanel);
        allPanels.add(avgAccuracyPanel);
        allPanels.add(panel5_5);
        allPanels.add(miscButtonsPanel);
        allPanels.add(panel7);
        allPanels.add(scrollToTopPanel);
        allPanels.add(scrollToBottomPanel);
        allPanels.add(scrollUpPanel);
        allPanels.add(scrollDownPanel);
        allPanels.add(saveButtonPanel);

        // add grid panels TODO
        // AMINE DONT FORGET FOR DARK MODE!!!!

        allPanels.add(scrollToTopPanelGridPanel1);
        allPanels.add(scrollToTopPanelGridPanel2);
        allPanels.add(scrollToTopPanelGridPanel3);

        allPanels.add(scrollToBottomPanelGridPanel1);
        allPanels.add(scrollToBottomPanelGridPanel2);
        allPanels.add(scrollToBottomPanelGridPanel3);

        allPanels.add(scrollUpPanelGridPanel1);
        allPanels.add(scrollUpPanelGridPanel2);
        allPanels.add(scrollUpPanelGridPanel3);

        allPanels.add(scrollDownPanelGridPanel1);
        allPanels.add(scrollDownPanelGridPanel2);
        allPanels.add(scrollDownPanelGridPanel3);

        allPanels.add(saveButtonPanelGridPanel1);
        allPanels.add(saveButtonPanelGridPanel2);
        allPanels.add(saveButtonPanelGridPanel3);
        

        middleMiddlePanel.add(panel1);
        middleMiddlePanel.add(numOfGamesPanel);
        middleMiddlePanel.add(avgRawPanel);
        middleMiddlePanel.add(avgActualPanel);
        middleMiddlePanel.add(avgAccuracyPanel);
        middleMiddlePanel.add(panel5_5);
        middleMiddlePanel.add(miscButtonsPanel);
        middleMiddlePanel.add(panel7);
        middleMiddlePanel.add(scrollToTopPanel);
        middleMiddlePanel.add(scrollToBottomPanel);
        middleMiddlePanel.add(scrollUpPanel);
        middleMiddlePanel.add(scrollDownPanel);
        middleMiddlePanel.add(saveButtonPanel);



        // end of middleMiddlePanel
        

        middleLeftPanel.add(leftTextBar, BorderLayout.NORTH);
        middleLeftPanel.add(pickStatPanel);

        JPanel middleRightPanel = new JPanel(new BorderLayout());
        middleRightPanel.setPreferredSize(new Dimension(200, 500));
        allPanels.add(middleRightPanel);

        JPanel rightTextBar = new JPanel();
        rightTextBar.setPreferredSize(new Dimension(200,30));
        rightTextBar.setBackground(Color.cyan);
        allPanels.add(rightTextBar);

        JLabel pickSettings = new JLabel("Choose a Map");
        rightTextBar.add(pickSettings);
        allLabels.add(pickSettings);

        pickMapPanel = new JPanel(new GridLayout(10,1));
        pickMapPanel.setPreferredSize(new Dimension(200, middleRightPanel.getHeight()-rightTextBar.getHeight()));
        pickMapPanel.setBackground(Color.darkGray);
        allPanels.add(pickMapPanel);

        middleRightPanel.add(rightTextBar, BorderLayout.NORTH);
        middleRightPanel.add(pickMapPanel);

        middleCenterPanel.add(middleLeftPanel, BorderLayout.WEST);
        middleCenterPanel.add(middleMiddlePanel, BorderLayout.CENTER);
        middleCenterPanel.add(middleRightPanel, BorderLayout.EAST);

        // pickStatPanel implementation:

        JPanel slot1 = new JPanel(new GridLayout(1,1));
        JPanel slot2 = new JPanel(new GridLayout(1,1));
        JPanel slot3 = new JPanel(new GridLayout(1,1));
        JPanel slot4 = new JPanel(new GridLayout(1,1));
        JPanel slot5 = new JPanel(new GridLayout(1,1));
        JPanel slot6 = new JPanel(new GridLayout(1,1));
        JPanel slot7 = new JPanel(new GridLayout(1,1));
        JPanel slot8 = new JPanel(new GridLayout(1,1));
        JPanel slot9 = new JPanel(new GridLayout(1,1));
        JPanel slot10 = new JPanel(new GridLayout(1,1));

        slot1.setBackground(Color.darkGray);
        slot2.setBackground(Color.darkGray);
        slot3.setBackground(Color.darkGray);
        slot4.setBackground(Color.darkGray);
        slot5.setBackground(Color.darkGray);
        slot6.setBackground(Color.darkGray);
        slot7.setBackground(Color.darkGray);
        slot8.setBackground(Color.darkGray);
        slot9.setBackground(Color.darkGray);
        slot10.setBackground(Color.darkGray);

        // allPanels.add(slot1);
        // allPanels.add(slot2);
        // allPanels.add(slot3);
        // allPanels.add(slot4);
        // allPanels.add(slot5);
        // allPanels.add(slot6);
        // allPanels.add(slot7);
        // allPanels.add(slot8);
        // allPanels.add(slot9);
        // allPanels.add(slot10);

        panelSlots.add(slot1);
        panelSlots.add(slot2);
        panelSlots.add(slot3);
        panelSlots.add(slot4);
        panelSlots.add(slot5);
        panelSlots.add(slot6);
        panelSlots.add(slot7);
        panelSlots.add(slot8);
        panelSlots.add(slot9);
        panelSlots.add(slot10);

        pickStatPanel.add(slot1);
        pickStatPanel.add(slot2);
        pickStatPanel.add(slot3);
        pickStatPanel.add(slot4);
        pickStatPanel.add(slot5);
        pickStatPanel.add(slot6);
        pickStatPanel.add(slot7);
        pickStatPanel.add(slot8);
        pickStatPanel.add(slot9);
        pickStatPanel.add(slot10);

        // pickMapPanel implementation:

        JPanel slot1ForMap = new JPanel(new GridLayout(1,1));
        JPanel slot2ForMap = new JPanel(new GridLayout(1,1));
        JPanel slot3ForMap = new JPanel(new GridLayout(1,1));
        JPanel slot4ForMap = new JPanel(new GridLayout(1,1));
        JPanel slot5ForMap = new JPanel(new GridLayout(1,1));
        JPanel slot6ForMap = new JPanel(new GridLayout(1,1));
        JPanel slot7ForMap = new JPanel(new GridLayout(1,1));
        JPanel slot8ForMap = new JPanel(new GridLayout(1,1));
        JPanel slot9ForMap = new JPanel(new GridLayout(1,1));
        JPanel slot10ForMap = new JPanel(new GridLayout(1,1));

        slot1ForMap.setBackground(Color.darkGray);
        slot2ForMap.setBackground(Color.darkGray);
        slot3ForMap.setBackground(Color.darkGray);
        slot4ForMap.setBackground(Color.darkGray);
        slot5ForMap.setBackground(Color.darkGray);
        slot6ForMap.setBackground(Color.darkGray);
        slot7ForMap.setBackground(Color.darkGray);
        slot8ForMap.setBackground(Color.darkGray);
        slot9ForMap.setBackground(Color.darkGray);
        slot10ForMap.setBackground(Color.darkGray);

        // allPanels.add(slot1ForMap);
        // allPanels.add(slot2ForMap);
        // allPanels.add(slot3ForMap);
        // allPanels.add(slot4ForMap);
        // allPanels.add(slot5ForMap);
        // allPanels.add(slot6ForMap);
        // allPanels.add(slot7ForMap);
        // allPanels.add(slot8ForMap);
        // allPanels.add(slot9ForMap);
        // allPanels.add(slot10ForMap);

        panelSlotsForMaps.add(slot1ForMap);
        panelSlotsForMaps.add(slot2ForMap);
        panelSlotsForMaps.add(slot3ForMap);
        panelSlotsForMaps.add(slot4ForMap);
        panelSlotsForMaps.add(slot5ForMap);
        panelSlotsForMaps.add(slot6ForMap);
        panelSlotsForMaps.add(slot7ForMap);
        panelSlotsForMaps.add(slot8ForMap);
        panelSlotsForMaps.add(slot9ForMap);
        panelSlotsForMaps.add(slot10ForMap);

        pickMapPanel.add(slot1ForMap);
        pickMapPanel.add(slot2ForMap);
        pickMapPanel.add(slot3ForMap);
        pickMapPanel.add(slot4ForMap);
        pickMapPanel.add(slot5ForMap);
        pickMapPanel.add(slot6ForMap);
        pickMapPanel.add(slot7ForMap);
        pickMapPanel.add(slot8ForMap);
        pickMapPanel.add(slot9ForMap);
        pickMapPanel.add(slot10ForMap);

        
        // Save button

        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(85,25));

        saveButtonPanelGridPanel1.add(saveButton);
        
        // scroll up button

        scrollUp = new JButton("Scroll Up");
        scrollUp.setPreferredSize(new Dimension(85, 25));

        scrollUpPanelGridPanel1.add(scrollUp);

        // scroll down button

        scrollDown = new JButton("Scroll Down");
        scrollDown.setPreferredSize(new Dimension(100,25));
        scrollDownPanelGridPanel1.add(scrollDown);

        // scroll all the way to the top
        
        scrollToTopForSaves = new JButton("Scroll to top");
        scrollToTopForSaves.setPreferredSize(new Dimension(110, 25));

        scrollToTopPanelGridPanel1.add(scrollToTopForSaves);

        // scroll all the way to the bottom

        scrollToBottomForSaves = new JButton("Scroll to bottom");
        scrollToBottomForSaves.setPreferredSize(new Dimension(135,25));

        scrollToBottomPanelGridPanel1.add(scrollToBottomForSaves);

        // miscButtons 

        seeLastButton = new JButton("See Last");
        seeLastButton.setPreferredSize(new Dimension(100, 25));
        
        seeLastGridPanel1.add(seeLastButton);

        seeCurrentButton = new JButton("See Current");
        seeCurrentButton.setPreferredSize(new Dimension(100, 25));

        seeCurrentGridPanel2.add(seeCurrentButton);

        seeTodayButton = new JButton("See Today");
        seeTodayButton.setPreferredSize(new Dimension(100, 25));

        seeTodayGridPanel3.add(seeTodayButton);

        // refresh button for maps

        settingsButtonForMaps = new JButton("Settings");
        settingsButtonForMaps.setPreferredSize(new Dimension(100, 25));

        saveButtonPanelGridPanel3.add(settingsButtonForMaps);

        // scroll up button for maps

        scrollUpButtonForMaps = new JButton("Scroll Up");
        scrollUpButtonForMaps.setPreferredSize(new Dimension(85, 25));

        scrollUpPanelGridPanel3.add(scrollUpButtonForMaps);

        // scroll down button for maps

        scrollDownButtonForMaps = new JButton("Scroll Down");
        scrollDownButtonForMaps.setPreferredSize(new Dimension(100, 25));

        scrollDownPanelGridPanel3.add(scrollDownButtonForMaps);

        // text bar for maps

        searchBar = new JTextField();
        searchBar.setPreferredSize(new Dimension(85,20));

        searchBar.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyCode());
                if (e.getKeyCode() == 10) {
                    search();
                }
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }
            
        });

        scrollToBottomPanelGridPanel3.add(searchBar);

        // status message for errors for text bar

        statusMessage = new JLabel();
        statusMessage.setFont(new Font("SansSerif", Font.PLAIN, 9));
        allLabels.add(statusMessage);
        //statusMessage.setPreferredSize(new Dimension(100, 100));
        
        scrollToBottomPanelGridPanel2GridPanel2.add(statusMessage, BorderLayout.EAST);

        // search button for maps

        previewButton = new JButton("Preview");
        previewButton.setPreferredSize(new Dimension(100, 25));

        scrollToTopPanelGridPanel3.add(previewButton);


        // end of middle panels

        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setPreferredSize(new Dimension(frame.getWidth(), 100));
        bottomPanel.setBackground(Color.gray);

        returnButton = new JButton("Return");
        returnButton.setPreferredSize(new Dimension(85,40));

        bottomPanel.add(returnButton);

        framePanel.add(topPanel, BorderLayout.NORTH);
        framePanel.add(middleCenterPanel, BorderLayout.CENTER);
        framePanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(framePanel);
        frame.setVisible(false);

        test();
        // addMapButton(wordBank.getMaps());
        //loadMapsOnStart();
        updateMapBar();
        System.out.println("Selected Num: " + mapSettingsFrame.getSettings().get("SelectedNumOfWords"));

        // mapSettingsFrame implementation:

        settingsButtonForMaps.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mapSettingsFrame.getFrame().setVisible(true);
                frame.setVisible(false);
                
            }
            
        });

        mapSettingsFrame.getReturnButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateMapBar();
                frame.setVisible(true);
            }
            
        });
    }

    public static void updateMapBar() {

        mapSettingsFrame.getFrame().setVisible(false);

        wordBank.updateModifiedMaps(mapSettingsFrame.getSettings());
        resetMapsOnBar();
        
        // should we apply settings?
        if (mapSettingsFrame.getSettings().get("Apply Settings").equals("true")) {
            System.out.println("inside");
            loadMaps(wordBank.getModifiedMaps());
        } else {
            loadMaps(wordBank.getMaps());
        }

        if (pickMapButtons.size()-1 < selectedMapIndex) {
            selectedMapIndex = -1; // reset selectedMapIndex if after applying settings the selected map is not shown
        }

    }

    public JFrame getFrame() {
        return frame;
    }

    public JButton getReturnButton() {
        return returnButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JLabel getNumOfGamesLabel() {
        return numOfGames;
    }

    public JLabel getAvgRawLabel() {
        return avgRaw;
    }

    public JLabel getAvgActualLabel() {
        return avgActual;
    }

    public JLabel getAvgAccuracyLabel() {
        return avgAccuracy;
    }

    public int getSelectedMapIndex() {
        return selectedMapIndex;
    }

    public static void addButton() {
        name++;
        mJButton button = new mJButton(name);
        if (indexSlot < 11) { // for the case where you click save and you are not at the bottom of the list
            indexSlot++; // keeps track of how many elements are in shownButtons
        }
        
        button.setText(String.valueOf("Save: " + name));

        System.out.println("indexSlot: " + indexSlot);

        pickStatButtons.add(button);

        if (isFirstTime) { // split into 2 to prevent error
            for (JPanel panel: panelSlots) { // resets panelSlots
                panel.removeAll();
            }
            if (indexSlot == 11) {
                shownButtons.remove(0);
                indexSlot--;
            }
            if (indexSlot < 11) {
                if (indexSlot == 1) {
                    shownButtons.add(pickStatButtons.get(0));
                }
                else {
                    shownButtons.add(pickStatButtons.get(shownButtons.get(shownButtons.size()-1).getNumber()));
                }
                
                indexForSaves++; // holds position in pickStatButtons
            }

            for (int i = 0; i < indexSlot && i < 10; i ++) {
                panelSlots.get(i).add(shownButtons.get(i));
            }
            isFirstTime = false;
        } else {

            if (pickStatButtons.get(pickStatButtons.size()-1).getNumber() - shownButtons.get(shownButtons.size()-1).getNumber() == 1 ) { // only allows adding button to the end if it is at the bottom of the list
                /* if conditional doesn't make sense uncomment this
                System.out.println("true");
                System.out.println(shownButtons.get(shownButtons.size()-1).getNumber() +" "+ pickStatButtons.get(pickStatButtons.size()-1).getNumber());
                */

                for (JPanel panel: panelSlots) { // resets panelSlots
                    panel.removeAll();
                }
                if (indexSlot == 11) {
                    shownButtons.remove(0);
                    indexSlot--;
                }
                if (indexSlot < 11) {
                    //System.out.println("in here LMAO");
                    if (indexSlot == 1) {
                        shownButtons.add(pickStatButtons.get(0));
                    }
                    else {
                        shownButtons.add(pickStatButtons.get(shownButtons.get(shownButtons.size()-1).getNumber()));
                    }
                    
                    indexForSaves++;
                }

                for (int i = 0; i < indexSlot && i < 10; i ++) {
                    panelSlots.get(i).add(shownButtons.get(i));
                }
            
            } 

        System.out.println("size: " + pickStatButtons.size());
        }




        frame.validate();
        frame.repaint();

    }

    public static void scrollUpForSaves() {
        System.out.println("indexSlot: " + indexSlot);
        for (JPanel panel: panelSlots) { // resets panelSlots
            panel.removeAll();
        }
        System.out.println(indexForSaves);
        if (pickStatButtons.size() > 10 && indexForSaves != 10) {
            shownButtons.remove(shownButtons.size()-1);
            shownButtons.add(0, pickStatButtons.get(indexForSaves-11)); // get previous button
            indexForSaves--;
        }

        for (int i = 0; i < indexSlot && i < 10; i ++) {
            panelSlots.get(i).add(shownButtons.get(i));
        }

        frame.validate();
        frame.repaint();
    }

    public static void scrollDownForSaves() {
        System.out.println("indexSlot: " + indexSlot);
        for (JPanel panel: panelSlots) { // resets panelSlots
            panel.removeAll();
        }
        if (pickStatButtons.size() > 10 && !(shownButtons.get(shownButtons.size()-1).getNumber() == pickStatButtons.get(pickStatButtons.size()-1).getNumber())) {
            System.out.print("in");
            shownButtons.remove(0);

            shownButtons.add(pickStatButtons.get(shownButtons.get(shownButtons.size()-1).getNumber()));
            System.out.println("Name: " + shownButtons.get(shownButtons.size()-1).getNumber());
            indexForSaves++;
        }

        for (int i = 0; i < indexSlot && i < 10; i ++) {
            panelSlots.get(i).add(shownButtons.get(i));
        }

        frame.validate();
        frame.repaint();

    }

    public static void scrollToTopForSaves() {
        if (pickStatButtons.size() > 10) {
            for (JPanel panel: panelSlots) { // resets panelSlots
                panel.removeAll();
            }
            for (int i = 0; i < 10; i ++) {
                shownButtons.remove(0);
            }
    
            for (int buttonNumber = 0; buttonNumber < 10; buttonNumber++) {
                shownButtons.add(pickStatButtons.get(buttonNumber));
            }

            for (int i = 0; i < indexSlot && i < 10; i ++) {
                panelSlots.get(i).add(shownButtons.get(i));
            }
    
            frame.validate();
            frame.repaint();

            indexForSaves = 10;
        }
    
    }

    public static void scrollToBottomForSaves() {
        if (pickStatButtons.size() > 10) {
            for (JPanel panel: panelSlots) { // resets panelSlots
                panel.removeAll();
            }
            for (int i = 0; i < 10; i ++) {
                shownButtons.remove(0);
            }

            for (int buttonNumber = pickStatButtons.size()-10; buttonNumber < pickStatButtons.size(); buttonNumber++) {
                shownButtons.add(pickStatButtons.get(buttonNumber));
            }

            for (int i = 0; i < indexSlot && i < 10; i ++) {
                panelSlots.get(i).add(shownButtons.get(i));
            }
    
            frame.validate();
            frame.repaint();

            indexForSaves = pickStatButtons.size();


        }
    }

    public static void scrollUpForMaps() {
        for (JPanel panel: panelSlotsForMaps) {
            panel.removeAll();
        }
        if (pickMapButtons.size() > 10 && indexForMaps != 10) {
            shownButtonsForMaps.remove(shownButtonsForMaps.size()-1);
            shownButtonsForMaps.add(0, pickMapButtons.get(indexForMaps-11));
            indexForMaps--;
        }

        for (int i = 0; i < indexSlotForMaps && i < 10; i++) {
            panelSlotsForMaps.get(i).add(shownButtonsForMaps.get(i));
        }

        frame.validate();
        frame.repaint();
    }

    public static void scrollDownForMaps() {
        for (JPanel panel: panelSlotsForMaps) {
            panel.removeAll();
        }
        if (pickMapButtons.size() > 10 && !(shownButtonsForMaps.get(shownButtonsForMaps.size()-1).getNumber() == pickMapButtons.get(pickMapButtons.size()-1).getNumber())) {
            shownButtonsForMaps.remove(0);

            shownButtonsForMaps.add(pickMapButtons.get(shownButtonsForMaps.get(shownButtonsForMaps.size()-1).getNumber()));
            indexForMaps++;
        }

        for (int i = 0; i < indexForMaps && i < 10; i ++) {
            panelSlotsForMaps.get(i).add(shownButtonsForMaps.get(i));
        }

        frame.validate();
        frame.repaint();
    }   

    public static void addButtonForSaves(String title, Stat stat) {
        name++;
        mJButton button = new mJButton(name);
        if (indexSlot < 11) { // for the case where you click save and you are not at the bottom of the list
            indexSlot++; // keeps track of how many elements are in shownButtons
        }
        //button.setBorder(BorderFactory.createEtchedBorder());
        
        allButtons.add(button);
        button.setText(title);

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("why am I here");

                numOfGames.setText("Number of Games: " + String.valueOf(stat.getGames()));
                avgRaw.setText("Average WPM (Raw): " + String.valueOf(stat.getAvgWpmRaw()));
                avgActual.setText("Average WPM (Actual): " + String.valueOf(stat.getAvgWpmActual()));
                avgAccuracy.setText("Average Accuracy: " + String.valueOf(stat.getAvgAccuracy()) + "%");

                System.out.println("stat.getGames(): " + stat.getGames());
                
            }
            
        });

        //System.out.println("indexSlot: " + indexSlot);

        pickStatButtons.add(button);

        if (isFirstTime) { // split into 2 to prevent error
            for (JPanel panel: panelSlots) { // resets panelSlots
                panel.removeAll();
            }
            if (indexSlot == 11) {
                shownButtons.remove(0);
                indexSlot--;
            }
            if (indexSlot < 11) {
                if (indexSlot == 1) {
                    shownButtons.add(pickStatButtons.get(0));
                }
                else {
                    shownButtons.add(pickStatButtons.get(shownButtons.get(shownButtons.size()-1).getNumber()));
                }
                
                indexForSaves++; // holds position in pickStatButtons
            }

            for (int i = 0; i < indexSlot && i < 10; i ++) {
                panelSlots.get(i).add(shownButtons.get(i));
            }
            isFirstTime = false;
        } else {

            if (pickStatButtons.get(pickStatButtons.size()-1).getNumber() - shownButtons.get(shownButtons.size()-1).getNumber() == 1 ) { // only allows adding button to the end if it is at the bottom of the list
                /* if conditional doesn't make sense uncomment this
                System.out.println("true");
                System.out.println(shownButtons.get(shownButtons.size()-1).getNumber() +" "+ pickStatButtons.get(pickStatButtons.size()-1).getNumber());
                */

                for (JPanel panel: panelSlots) { // resets panelSlots
                    panel.removeAll();
                }
                if (indexSlot == 11) {
                    shownButtons.remove(0);
                    indexSlot--;
                }
                if (indexSlot < 11) {
                    //System.out.println("in here LMAO");
                    if (indexSlot == 1) {
                        shownButtons.add(pickStatButtons.get(0));
                    }
                    else {
                        shownButtons.add(pickStatButtons.get(shownButtons.get(shownButtons.size()-1).getNumber()));
                    }
                    
                    indexForSaves++;
                }

                for (int i = 0; i < indexSlot && i < 10; i ++) {
                    panelSlots.get(i).add(shownButtons.get(i));
                }
            
            } 

        System.out.println("size: " + pickStatButtons.size());
        }


        if (isInDarkMode) {
            darkMode(true);
        } else {
            darkMode(false);
        }
        
        frame.validate();
        frame.repaint();

    }

    public static void test() throws FileNotFoundException {

        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<Stat> madeStats = new ArrayList<Stat>();
        ArrayList<String> sortedTitles = new ArrayList<String>();

        int numOfFiles = 0;

        File directory = new File(System.getProperty("user.dir") + "/SpeedType/Saves/");

        ArrayList<File> sortedFiles = new ArrayList<File>();
        int index = directory.listFiles().length;

        for (File file: directory.listFiles()) { // sort files
            sortedFiles.add(directory.listFiles()[index-1]);
            index--;
            numOfFiles++;
        }
        

        Collections.sort(sortedFiles);
        for (File file: sortedFiles) { // get stats and title
            String[] temp = file.getName().split("Save ");
            temp = temp[1].split("-");
            //System.out.println(Arrays.toString(temp));
            String title = temp[0] + "/" + temp[1] + "/" + temp[2].substring(0, temp[2].length()-4);
            titles.add(title);

            System.out.println(file.getName());

            Scanner scanner = new Scanner(file);
            Stat stat = new Stat();

            while (scanner.hasNextLine()) {
                String currentLine = "";
                String[] tempCheck;
                currentLine = scanner.nextLine();
                tempCheck = currentLine.split(" ");
                if (currentLine.startsWith("Number of Games: ")) {
                    gamesPlayed = Integer.valueOf(tempCheck[tempCheck.length-1]);
                    allTimeStats.addGameByAmount(Integer.valueOf(tempCheck[tempCheck.length-1]));
                }
                if (currentLine.startsWith("Average WPM (Raw): ")) {
                    avgRaw1 = Double.parseDouble(tempCheck[tempCheck.length-1]);
                    allTimeStats.addRaw(Double.parseDouble(tempCheck[tempCheck.length-1])); 
                }
                if (currentLine.startsWith("Average WPM (Actual): ")) {
                    avgActual1 = Double.parseDouble(tempCheck[tempCheck.length-1]);
                    allTimeStats.addActual(Double.parseDouble(tempCheck[tempCheck.length-1]));
                }
                if (currentLine.startsWith("Average Accuracy: ")) {
                    avgAccuracy1 = Double.parseDouble(tempCheck[tempCheck.length-1]);
                    allTimeStats.addAccuracy(Double.parseDouble(tempCheck[tempCheck.length-1])); 
                }

                //System.out.println("FileNum:" + fileNum + "    avgRaw1: " + avgRaw1);
                stat.setStats(gamesPlayed, avgRaw1, avgActual1, avgAccuracy1);
                

                
        }
        madeStats.add(stat);

        //System.out.println();
        scanner.close();
    }
    int idx = 0;

    // change shown buttons and change pickstatButtons

    if (shownButtons.size() != 0 ) {
        for (int i = 0; i < shownButtons.size(); i ++) {
            System.out.println("true");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");

            if (shownButtons.get(i).getText().equals(simpleDateFormat.format(currentDate))) {
                System.out.println("in");
                for (ActionListener al : shownButtons.get(i).getActionListeners()) {
                    shownButtons.get(i).removeActionListener(al);
                }
                File directoryForThis = new File(System.getProperty("user.dir") + "/SpeedType/Saves/" + "Save " + timeFormat.format(currentDate) + ".txt");
                Stat stat = new Stat();
                Scanner scanner = new Scanner(directoryForThis);
    
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
    
                    //System.out.println("FileNum:" + fileNum + "    avgRaw1: " + avgRaw1);
                    stat.setStats(gamesPlayed, avgRaw1, avgActual1, avgAccuracy1);
    
                    shownButtons.get(i).addActionListener(new ActionListener() {
    
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.print("Clicking this");

                        numOfGames.setText("Number of Games: " + String.valueOf(stat.getGames()));
                        avgRaw.setText("Average WPM (Raw): " + String.valueOf(stat.getAvgWpmRaw()));
                        avgActual.setText("Average WPM (Actual): " + String.valueOf(stat.getAvgWpmActual()));
                        avgAccuracy.setText("Average Accuracy: " + String.valueOf(stat.getAvgAccuracy()) + "%");
                        
                    }
                    
                });
            }
            scanner.close();
        }
    }
    }
    
    int stat1 = 0;
    for (Stat stat : madeStats) {
        //System.out.println("Stat: " + stat1);
        //System.out.println("Title: " + titles.get(stat1));
        stat1++;
        
    }
    

    if (shownButtons.size() == sortedFiles.size()) {
        return;
    }
    for (String temp: titles) {
        if (shownButtons.size() == numOfFiles) {
            return;
        }

        
        addButtonForSaves(titles.get(idx), madeStats.get(idx));
        idx++;
    }
    }

    public static void addTodaysStats() throws FileNotFoundException {
        /* 
        if (!isTodaysStatPresent()) {
            return;
        } else {
            addButtonForSaves("placeholder", new Stat());
        }
        */

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        if (pickStatButtons.size() > 0 && pickStatButtons.get(pickStatButtons.size()-1).getText().equals(simpleDateFormat.format(currentDate))) {
            mJButton button = pickStatButtons.get(pickStatButtons.size()-1);
            System.out.println(button == shownButtons.get(shownButtons.size()-1));

            for (ActionListener al: button.getActionListeners()) {
                button.removeActionListener(al);
            }

            File directoryForThis = new File(System.getProperty("user.dir") + "/SpeedType/Saves/" + "Save " + timeFormat.format(currentDate) + ".txt");
            Stat stat = new Stat();
            Scanner scanner = new Scanner(directoryForThis);
            

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

                //System.out.println("FileNum:" + fileNum + "    avgRaw1: " + avgRaw1);
                stat.setStats(gamesPlayed, avgRaw1, avgActual1, avgAccuracy1);

                button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.print("Clicking this");

                    numOfGames.setText("Number of Games: " + String.valueOf(stat.getGames()));
                    avgRaw.setText("Average WPM (Raw): " + String.valueOf(stat.getAvgWpmRaw()));
                    avgActual.setText("Average WPM (Actual): " + String.valueOf(stat.getAvgWpmActual()));
                    avgAccuracy.setText("Average Accuracy: " + String.valueOf(stat.getAvgAccuracy()) + "%");
                    
                }
                
            });
        
        }
        scanner.close();

    } else {
            mJButton button = null;
            if (shownButtons.size() > 0) {
                button = shownButtons.get(shownButtons.size()-1);
            } else {
                button = new mJButton(1);
            }
            

            File directoryForThis = new File(System.getProperty("user.dir") + "/SpeedType/Saves/" + "Save " + timeFormat.format(currentDate) + ".txt");
            Stat stat = new Stat();
            Scanner scanner = new Scanner(directoryForThis);

            String[] temp = directoryForThis.getName().split("Save ");
            temp = temp[1].split("-");
            //System.out.println(Arrays.toString(temp));
            String title = temp[0] + "/" + temp[1] + "/" + temp[2].substring(0, temp[2].length()-4);
            

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

                //System.out.println("FileNum:" + fileNum + "    avgRaw1: " + avgRaw1);
                stat.setStats(gamesPlayed, avgRaw1, avgActual1, avgAccuracy1);

                button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.print("Clicking this");

                    numOfGames.setText("Number of Games: " + String.valueOf(stat.getGames()));
                    avgRaw.setText("Average WPM (Raw): " + String.valueOf(stat.getAvgWpmRaw()));
                    avgActual.setText("Average WPM (Actual): " + String.valueOf(stat.getAvgWpmActual()));
                    avgAccuracy.setText("Average Accuracy: " + String.valueOf(stat.getAvgAccuracy()) + "%");
                    
                }
                
            });
        
        }
        scanner.close();

        //pickStatButtons.get(pickStatButtons.size()-1).setText(title);
        addButtonForSaves(title, stat);
    }
}

    // if carry saves is set to true, set dashboard to reflect stats
    public static void updateDashboard(Map<String, String> settings, Player player) throws FileNotFoundException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        
        if (settings.get("carry save").equals("true")) {
            if (pickStatButtons.size() > 0 && pickStatButtons.get(pickStatButtons.size()-1).getText().equals(simpleDateFormat.format(currentDate))) {
                numOfGames.setText(String.valueOf("Number of Games: " + player.getGames()));
                avgRaw.setText(String.valueOf("Average WPM (Raw): " + player.avgRaw()));
                avgActual.setText(String.valueOf("Average WPM (Actual): " + player.avgActual()));
                avgAccuracy.setText(String.valueOf("Average Accuracy: " + player.avgAccuracy()) + "%");
        }
        }
    }

    public static boolean isTodaysStatPresent() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        if (pickStatButtons.size() > 0) {
            if (pickStatButtons.get(pickStatButtons.size()-1).getText().equals(simpleDateFormat.format(currentDate))) {
                return true;
            }
        }
        return false;
    }

    public void showSpecificStats(Stat stat) {
        numOfGames.setText(String.valueOf("Number of Games: " + stat.getGames()));
        avgRaw.setText(String.valueOf("Average WPM (Raw): " + stat.getAvgWpmRaw()));
        avgActual.setText(String.valueOf("Average WPM (Actual): " + stat.getAvgWpmActual()));
        avgAccuracy.setText(String.valueOf("Average Accuracy: " + stat.getAvgAccuracy()) + "%");
    }
    
    public static void addMapButton(String title) {
        nameForMaps++;
        mJButton button = new mJButton(nameForMaps);
        if (indexSlotForMaps < 11) {
            indexSlotForMaps++;
        }

        allButtons.add(button);

        //button.setText(String.valueOf(nameForMaps + ". " + maps.getDefinition(nameForMaps-1).substring(0, 10) + "..."));
        button.setText(title);

        pickMapButtons.add(button);
        // iterate through pickMapButtons and make every button unselected except clicked button

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                button.setForeground(Color.decode("#F9D092"));
                selectedMapIndex = button.getNumber() - 1;
                System.out.println("selectedMapIndex: " + selectedMapIndex);

                for (mJButton MJButton: pickMapButtons) {
                    if (MJButton.getNumber() != button.getNumber()) {
                        if (isInDarkMode) {
                            MJButton.setForeground(Color.white);
                        } else {
                            MJButton.setForeground(Color.black);
                        }
                    }
                }
                
            }
            
        });

        if (isFirstTimeForMaps) {
            for (JPanel panel: panelSlotsForMaps) {
                panel.removeAll();
            }
            if (indexSlotForMaps == 11) {
                shownButtonsForMaps.remove(0);
                indexSlotForMaps--;
            }
            if (indexSlotForMaps < 11) {
                if (indexSlotForMaps == 1) {
                    shownButtonsForMaps.add(pickMapButtons.get(0));
                } else {
                    shownButtonsForMaps.add(pickMapButtons.get(shownButtonsForMaps.get(shownButtonsForMaps.size()-1).getNumber()));
                }

                indexForMaps++; // hold position in pickMapButtons
            }

            for (int i = 0; i < indexSlotForMaps && i < 10; i ++) {
                panelSlotsForMaps.get(i).add(shownButtonsForMaps.get(i));
            }
            isFirstTime = false;
        } else {

            if (pickMapButtons.get(pickMapButtons.size()-1).getNumber() - shownButtonsForMaps.get(shownButtonsForMaps.size()-1).getNumber() == 1 ) {

                for (JPanel panel: panelSlotsForMaps) {
                    panel.removeAll();
                }
                if (indexSlotForMaps == 11) {
                    shownButtonsForMaps.remove(0);
                    indexSlotForMaps--;
                }
                if (indexSlotForMaps < 11) {
                    if (indexSlotForMaps == 1) {
                        shownButtonsForMaps.add(pickMapButtons.get(0));
                    }
                    else {
                        shownButtonsForMaps.add(pickMapButtons.get(shownButtonsForMaps.get(shownButtonsForMaps.size()-1).getNumber()));
                        }

                        indexForMaps++;
                    }

                    for (int i = 0; i < indexSlotForMaps && i < 10; i++) {
                        panelSlotsForMaps.get(i).add(shownButtonsForMaps.get(i));
                    }
                }
            }

            if (isInDarkMode) {
                darkMode(true);
            } else {
                darkMode(false);
            }

            frame.validate();
            frame.repaint();
    }

    public static void search() {
        userInput = searchBar.getText();
        searchBar.setText("");

        boolean keyWordUsed = false;

        if (userInput.equals("top")) {
            scrollToTopForMaps();
            keyWordUsed = true;
            statusMessage.setText("");
        } else if (userInput.equals("bottom")) {
            scrollToBottomForMaps();
            keyWordUsed = true;
            statusMessage.setText("");
        }

        boolean isInteger = true;
        try {
            Integer.parseInt(userInput);
            isInteger = true;
        } catch (Exception e) {
            //TODO: handle exception
            isInteger = false;
        }

        if (isInteger) {
            if ( !(Integer.valueOf(userInput) < 1) && !(Integer.valueOf(userInput) > pickMapButtons.size()) ) { // do the big things here
                statusMessage.setText("");

                if (Integer.valueOf(userInput) < 10) { // scroll to the top if answer is < 10
                    scrollToTopForMaps();
                } else if ((pickMapButtons.size() - Integer.valueOf(userInput)) < 10) { // if answer is within 10 from the top scroll to the bottom
                    scrollToBottomForMaps();
                } else { // show the selection in the middle if answer is more than 10 but below within 10 from the top
                    scrollToMiddleForMaps();
                }
            } else {
                statusMessage.setText("Out of Range!");
            }
        } else {
            if (!keyWordUsed) {
                statusMessage.setText("Only Integers!");
            }
        }

    }

    public static void scrollToTopForMaps() {
        if (pickMapButtons.size() > 10) {
            for (JPanel panel: panelSlotsForMaps) {
                panel.removeAll();
            }
            for (int i = 0; i < 10; i ++) {
                shownButtonsForMaps.remove(0);
            }

            for (int buttonNumber = 0; buttonNumber < 10; buttonNumber++) {
                shownButtonsForMaps.add(pickMapButtons.get(buttonNumber));
            }

            for (int i = 0; i < indexSlotForMaps && i < 10; i++) {
                panelSlotsForMaps.get(i).add(shownButtonsForMaps.get(i));
            }

            frame.validate();
            frame.repaint();

            indexForMaps = 10;
        }
    }

    public static void scrollToMiddleForMaps() {
        if (pickMapButtons.size() > 10) {
            for (JPanel panel: panelSlotsForMaps) {
                panel.removeAll();
            }
            for (int i = 0; i < 10; i ++) {
                shownButtonsForMaps.remove(0);
            }
            for (int i = Integer.valueOf(userInput) - 5; i < Integer.valueOf(userInput) + 5; i++) {
                shownButtonsForMaps.add(pickMapButtons.get(i));
            }

            for (int i = 0; i < indexSlotForMaps && i < 10; i++) {
                panelSlotsForMaps.get(i).add(shownButtonsForMaps.get(i));
            }

            frame.validate();
            frame.repaint();

            indexForMaps = Integer.valueOf(userInput) + 4;


        }
    }

    public static void scrollToBottomForMaps() {
        if (pickMapButtons.size() > 10) {
            for (JPanel panel: panelSlotsForMaps) { // resets panelSlots
                panel.removeAll();
            }
            for (int i = 0; i < 10; i ++) {
                shownButtonsForMaps.remove(0);
            }

            for (int buttonNumber = pickMapButtons.size()-10; buttonNumber < pickMapButtons.size(); buttonNumber++) {
                shownButtonsForMaps.add(pickMapButtons.get(buttonNumber));
            }

            for (int i = 0; i < indexSlotForMaps && i < 10; i ++) {
                panelSlotsForMaps.get(i).add(shownButtonsForMaps.get(i));
            }
    
            frame.validate();
            frame.repaint();

            indexForSaves = pickMapButtons.size();


        }
    }

    public void loadMapsOnStart() throws FileNotFoundException {
        File directory = new File(System.getProperty("user.dir") + "/SpeedType/textSettings.txt");
        Scanner scanner = new Scanner(directory);

        while (scanner.hasNextLine()) {
            String check = scanner.nextLine();

            if (check.split(" ")[check.split(" ").length-1].equals("true")) {
                check = scanner.nextLine();
                if (check.split(" ")[check.split(" ").length-1].equals("true")) { //should we apply settings?
                    System.out.println("Applying modified maps");
                    loadMaps(wordBank.getModifiedMaps());
                    break;
                } else {
                    System.out.println("we are not applying modified maps");
                    loadMaps(wordBank.getMaps());
                    break;
                }
                
            } else {
                System.out.println("not applying on startup");
                loadMaps(wordBank.getMaps());
                break;
            }
        }

        scanner.close();
    }

    public static void loadMaps(Terms maps) {

        int numOfMaps = 0;
        int name = 1;

        File directory = new File(System.getProperty("user.dir") + "/SpeedType/maps.txt");

        for (int i = 0; i < maps.getSize(); i++) {
            int endIndex = maps.getDefinition(i).length();
            addMapButton(String.valueOf(name + ". " + maps.getDefinition(i).substring(0, endIndex) + "..."));
            name++;
        }
        if (isInDarkMode) { // make sure the buttons are in the right color
            darkMode(true);
        } else {
            darkMode(false);
        }
    }

    public static void resetMapsOnBar() {
        pickMapButtons = new ArrayList<mJButton>();
        shownButtonsForMaps = new ArrayList<mJButton>();
        for (JPanel panel: panelSlotsForMaps) {
            panel.removeAll();
        }

        indexForMaps = 0;
        indexSlotForMaps = 0;
        nameForMaps = 0;

    }


    public void loadSettingsOntoMapSettingsFrame(Map<String,String> settings) {
        if (settings.get("dark/light mode").equals("dark")) {
            mapSettingsFrame.darkMode(true);
        } else {
            mapSettingsFrame.darkMode(false);
        }
    }
    
    public static void preview() {
        if (selectedMapIndex != -1) {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setSize(500,200);

            JPanel contentPanel = new JPanel(new FlowLayout());

            String splitThis;
            if (mapSettingsFrame.getSettings().get("Apply Settings").equals("true")) {
                splitThis = wordBank.getModifiedMaps().getDefinition(selectedMapIndex);
            } else {
                splitThis = wordBank.getMaps().getDefinition(selectedMapIndex);
            }
            
            String[] temp = splitThis.split(" ");

            for (String str: temp) {
                contentPanel.add(new JLabel(str));
            }

            frame.add(contentPanel);

            frame.setVisible(true);
        }
    }

    public static void darkMode(boolean bool) {
        if (bool) { // switch to dark
            isInDarkMode = true;
            for (JPanel panel: allPanels) {
                panel.setBackground(Color.decode("#323437"));
            }
            for (JLabel label: allLabels) {
                label.setForeground(Color.white);
            }
            for (JButton button: allButtons) {
                button.setBackground(Color.gray);
                button.setForeground(Color.white);
                button.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray));
            }
        } else { // switch to light
            isInDarkMode = false;
            for (JPanel panel: allPanels) {
                panel.setBackground(Color.white);
            }
            for (JLabel label: allLabels) {
                label.setForeground(Color.black);
            }
            for (JButton button: allButtons) {
                button.setBackground(Color.white);
                button.setForeground(Color.black);
                button.setBorderPainted(true);
                //button.setBorder(BorderFactory.createEmptyBorder());
                button.setBorder(UIManager.getBorder("Button.border"));
            }
        }
        // put back colored selectedMapIndex
        for (int i = 0; i < pickMapButtons.size(); i++) {
            if (i == selectedMapIndex) {
                pickMapButtons.get(i).setForeground(Color.decode("#F9D092"));
            }
        }
    }

    public ArrayList<mJButton> getShownButtons() {
        return shownButtons;
    }

    public JButton getScrollUpButtonForSaves() {
        return scrollUp;
    }

    public JButton getScrollDownButtonForSaves() {
        return scrollDown;
    }

    public JButton getScrollToTheTopButtonForSaves() {
        return scrollToTopForSaves;
    }

    public JButton getScrollToTheBottomButtonForSaves() {
        return scrollToBottomForSaves;
    }

    public JButton getSeeLastButton() {
        return seeLastButton;
    }

    public JButton getSeeCurrentButton() {
        return seeCurrentButton;
    }

    public JButton getSeeTodayButton() {
        return seeTodayButton;
    }

    public JButton getScrollUpButtonForMaps() {
        return scrollUpButtonForMaps;
    }

    public JButton getScrollDownButtonForMaps() {
        return scrollDownButtonForMaps;
    }

    public JButton getPreviewButtonForMaps() {
        return previewButton;
    }

    public JButton getSettingsButtonForMaps() {
        return settingsButtonForMaps;
    }

    public MapSettingsFrame getMapSettingsFrame() {
        return mapSettingsFrame;
    }

    public Wordbank getWordbank() {
        return wordBank;
    }

    public static void main(String[] args) throws IOException {
        StatsFrame statsFrame = new StatsFrame();
        statsFrame.getFrame().setVisible(true);
        //System.out.println("The answer is: " + isTodaysStatPresent());
        //statsFrame.resetMapsOnBar();


        statsFrame.getScrollUpButtonForSaves().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                scrollUpForSaves();
            }
            
        });

        statsFrame.getScrollDownButtonForSaves().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                scrollDownForSaves();
            }
            
        });

        statsFrame.getScrollToTheTopButtonForSaves().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                scrollToTopForSaves();
            }
            
        });

        statsFrame.getScrollToTheBottomButtonForSaves().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                scrollToBottomForSaves();
                
            }
            
        });

        statsFrame.getSaveButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addTodaysStats();
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
            
        });

        statsFrame.getScrollUpButtonForMaps().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                scrollUpForMaps();
            }
            
        });

        statsFrame.getScrollDownButtonForMaps().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                scrollDownForMaps();
                
            }
            
        });

        statsFrame.getPreviewButtonForMaps().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                preview();
            }
            
        });

        
        StatsFrame.darkMode(true);
        
        Map<String, String> settings = new HashMap<String,String>();
        settings.put("dark/light mode", "light");

        statsFrame.loadSettingsOntoMapSettingsFrame(settings);
    }
}
