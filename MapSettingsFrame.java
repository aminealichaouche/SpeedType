package SpeedType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapSettingsFrame implements ActionListener{
    
    private JFrame frame;
    private Map<String, String> settings;
    private ArrayList<JPanel> allPanels;
    private ArrayList<JLabel> allLabels;
    private ArrayList<JButton> allButtons;
    private ArrayList<JButton> theMapSizeButtons;

    private static JRadioButton allowPunctuationRadioButton;
    private static JRadioButton applySettingsRadioButton;
    private static JRadioButton loadSettingsRadioButton;
    private static JRadioButton randomizeMapsRadioButton;

    private static JButton returnButton;

    private boolean isAllowPunctuationClicked;
    private boolean isApplySettingsClicked;
    private boolean isSettingsLoadClicked;
    private boolean isTimeWordClicked;
    private boolean isRandomizeMapsClicked;

    private boolean isInDarkModeCurrently = false;

    private JButton number5Button;
    private JButton number10Button;
    private JButton number15Button;
    private JButton number20Button;
    private JButton anyButton;
    private int selectedNumOfWords = 1;

    private static JLabel timeLabel;
    private static JLabel wordLabel;
    private static JRadioButton chooseTimeWord;

    public MapSettingsFrame() throws IOException {
        settings = new HashMap<String, String>();
        allLabels = new ArrayList<JLabel>();
        allButtons = new ArrayList<JButton>();
        theMapSizeButtons = new ArrayList<JButton>();

        settings.put("Have Punctuation", "true");
        settings.put("Apply Settings", "false");
        settings.put("Time/Word Mode", "time");
        settings.put("SelectedNumOfWords", "1");
        settings.put("Randomize Maps", "true");

        frame = new JFrame();
        frame.setLayout(new GridLayout(10,2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);

        allPanels = new ArrayList<JPanel>();

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Map Settings");
        allLabels.add(title);

        JPanel panel2 = new JPanel();
        JPanel allowPunctuationPanel = new JPanel();

        JLabel allowPunctuationLabel = new JLabel("Allow Punctuation");
        allLabels.add(allowPunctuationLabel);
        allowPunctuationRadioButton = new JRadioButton();
        // set to be default on true
        isAllowPunctuationClicked = true;
        allowPunctuationRadioButton.setSelected(true);

        JPanel numOfWordsPanel = new JPanel(new FlowLayout());
        int width = 80;
        int height = 40;

        // two action commands because buttons correspond to different numbers depending
        // on if we are in time or words mode.

        number5Button = new JButton("10");
        number5Button.setPreferredSize(new Dimension(width,height));
        number5Button.setActionCommand("1");
        number5Button.addActionListener(this);

        number10Button = new JButton("15");
        number10Button.setPreferredSize(new Dimension(width,height));
        number10Button.setActionCommand("2");
        number10Button.addActionListener(this);

        number15Button = new JButton("20");
        number15Button.setPreferredSize(new Dimension(width,height));
        number15Button.setActionCommand("3");
        number15Button.addActionListener(this);

        number20Button = new JButton("30");
        number20Button.setPreferredSize(new Dimension(width,height));
        number20Button.setActionCommand("4");
        number20Button.addActionListener(this);

        anyButton = new JButton("Any");
        anyButton.setPreferredSize(new Dimension(width,height));
        anyButton.setActionCommand("-1");
        anyButton.addActionListener(this);

        allButtons.add(number5Button);
        allButtons.add(number10Button);
        allButtons.add(number15Button);
        allButtons.add(number20Button);
        allButtons.add(anyButton);

        theMapSizeButtons.add(number5Button);
        theMapSizeButtons.add(number10Button);
        theMapSizeButtons.add(number15Button);
        theMapSizeButtons.add(number20Button);
        theMapSizeButtons.add(anyButton);
        
        numOfWordsPanel.add(number5Button);
        numOfWordsPanel.add(number10Button);
        numOfWordsPanel.add(number15Button);
        numOfWordsPanel.add(number20Button);
        numOfWordsPanel.add(anyButton);

        JPanel timeOrWordsPanel = new JPanel();

        timeLabel = new JLabel("Time");
        wordLabel = new JLabel("Words");
        allLabels.add(timeLabel);
        allLabels.add(wordLabel);
        
        chooseTimeWord = new JRadioButton();

        JPanel randomizeMapsPanel = new JPanel();

        JLabel randomizeMapsLabel = new JLabel("Randomize Maps");
        allLabels.add(randomizeMapsLabel);
        randomizeMapsRadioButton = new JRadioButton();
        // set to be default on true
        isRandomizeMapsClicked = true;
        randomizeMapsRadioButton.setSelected(true);

        JPanel panel7 = new JPanel();
        JPanel applySettingsPanel = new JPanel();

        JLabel applySettingsLabel = new JLabel("Apply Settings");
        allLabels.add(applySettingsLabel);
        applySettingsRadioButton = new JRadioButton();

        JPanel loadSettingsOnStartPanel = new JPanel();

        JLabel loadSettingsLabel = new JLabel("Load Settings On Startup");
        allLabels.add(loadSettingsLabel);
        loadSettingsRadioButton = new JRadioButton();

        
        JPanel returnButtonPanel = new JPanel();

        returnButton = new JButton("Return");
        returnButton.setPreferredSize(new Dimension(85, 40));
        returnButton.setContentAreaFilled(false);
        returnButton.setOpaque(true);
        returnButton.setFocusable(false);
        allButtons.add(returnButton);

        allPanels.add(titlePanel);
        allPanels.add(panel2);
        allPanels.add(allowPunctuationPanel);
        allPanels.add(numOfWordsPanel);
        allPanels.add(timeOrWordsPanel);
        allPanels.add(randomizeMapsPanel);
        allPanels.add(panel7);
        allPanels.add(applySettingsPanel);
        allPanels.add(loadSettingsOnStartPanel);
        allPanels.add(returnButtonPanel);

        // ActionListeners:

        loadSettingsRadioButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                isSettingsLoadClicked = !isSettingsLoadClicked;

                try {
                    writeToSettings();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
            
        });

        allowPunctuationRadioButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                isAllowPunctuationClicked = !isAllowPunctuationClicked;
                allowPunctuationSwitcher();

                try {
                    writeToSettings();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                //System.out.println(settings.get("Have Punctuation"));
                
            }
            
        });

        applySettingsRadioButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                isApplySettingsClicked = !isApplySettingsClicked;
                applySettingsSwitcher();

                try {
                    writeToSettings();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
            
        });

        chooseTimeWord.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                isTimeWordClicked = !isTimeWordClicked;
                timeWordSwitcher();
                timeWordColorSwitcher();
                numWordTimeButtonChanger();

                try {
                    writeToSettings();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }

        });
        
        randomizeMapsRadioButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                isRandomizeMapsClicked = !isRandomizeMapsClicked;
                randomizeMapsSwitcher();

                try {
                    writeToSettings();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
            
        });

        for (JPanel panel: allPanels) {
            frame.add(panel);
        }

        // add components to panels
        titlePanel.add(title);

        allowPunctuationPanel.add(allowPunctuationLabel);
        allowPunctuationPanel.add(allowPunctuationRadioButton);

        applySettingsPanel.add(applySettingsLabel);
        applySettingsPanel.add(applySettingsRadioButton);

        loadSettingsOnStartPanel.add(loadSettingsLabel);
        loadSettingsOnStartPanel.add(loadSettingsRadioButton);

        timeOrWordsPanel.add(timeLabel);
        timeOrWordsPanel.add(chooseTimeWord);
        timeOrWordsPanel.add(wordLabel);

        randomizeMapsPanel.add(randomizeMapsLabel);
        randomizeMapsPanel.add(randomizeMapsRadioButton);

        returnButtonPanel.add(returnButton);

        loadSettingsOnStart();
        writeToSettings();
    }

    public void writeToSettings() throws IOException { 
        FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + "/SpeedType/textSettings.txt");
        String loadSettingsString;
        String allowPunctuationString;
        String applySettingsString;
        String timeWordModeString;
        String randomizeMapsString;

        if (isSettingsLoadClicked) {
            loadSettingsString = "true";
        } else {
            loadSettingsString = "false";
        }
        if (isAllowPunctuationClicked) {
            allowPunctuationString = "true";
        } else {
            allowPunctuationString = "false";
        }
        if (isApplySettingsClicked) {
            applySettingsString = "true";
        } else {
            applySettingsString = "false";
        }
        if (isTimeWordClicked) {
            timeWordModeString = "Word";
        } else {
            timeWordModeString = "Time";
        }
        if (isRandomizeMapsClicked) {
            randomizeMapsString = "true";
        } else {
            randomizeMapsString = "false";
        }



        fileWriter.write("Load on Startup: " + loadSettingsString + "\n");
        fileWriter.write("Apply Settings: " + applySettingsString + "\n");
        fileWriter.write("\n");
        fileWriter.write("Allow Punctuation: " + allowPunctuationString + "\n");
        fileWriter.write("Time/Word Mode: " + timeWordModeString + "\n");
        fileWriter.write("SelectedNumOfWords: " + selectedNumOfWords + "\n");
        fileWriter.write("Randomize Maps: " + randomizeMapsString);


        fileWriter.close();
    }

    public void loadSettingsOnStart() throws FileNotFoundException {
        File file = new File(System.getProperty("user.dir") + "/SpeedType/textSettings.txt");
        Scanner scanner = new Scanner(file);

        String check = scanner.nextLine();
        System.out.println(check);
        if (check.split(" ")[check.split(" ").length-1].equals("true")) { // see if we should load this
            isSettingsLoadClicked = true;
            loadSettingsRadioButton.setSelected(true);
            System.out.println("I say yes");
            check = scanner.nextLine();

            if (check.split(" ")[check.split(" ").length-1].equals("true")) { // Apply Settings
                isApplySettingsClicked = true;
                applySettingsRadioButton.setSelected(true);
            } else {
                isApplySettingsClicked = false;
            }
            applySettingsSwitcher();

            scanner.nextLine(); // skip blank space
            check = scanner.nextLine();

            if (check.split(" ")[check.split(" ").length-1].equals("true")) { // Allow Punctuation
                isAllowPunctuationClicked = true;
                loadSettingsRadioButton.setSelected(true);
            } else {
                isAllowPunctuationClicked = false;
                allowPunctuationRadioButton.setSelected(false);
            }
            allowPunctuationSwitcher();

            check = scanner.nextLine();

            if (check.split(" ")[check.split(" ").length-1].equals("Time")) { // Time/Word Mode
                isTimeWordClicked = false;
            } else {
                isTimeWordClicked = true;
                chooseTimeWord.setSelected(true);
            }
            timeWordSwitcher();
            timeWordColorSwitcher();

            check = scanner.nextLine();

            if (!check.split(" ")[check.split(" ").length-1].equals("0")) { // SelectedNumOfWords
                selectedNumOfWords = Integer.valueOf(check.split(" ")[check.split(" ").length-1]);
                System.out.println("SelectedNumOfWords for Start: " + selectedNumOfWords);
                settings.put("SelectedNumOfWords", String.valueOf(selectedNumOfWords));

                loadBackColorForButton();
            }

            check = scanner.nextLine();

            if (check.split(" ")[check.split(" ").length-1].equals("true")) { // Randomize Maps
                isRandomizeMapsClicked = true;
                randomizeMapsRadioButton.setSelected(true);
            } else {
                isRandomizeMapsClicked = false;
                randomizeMapsRadioButton.setSelected(false);
            }
            randomizeMapsSwitcher();


        } else {
            System.out.println("hey what do you know!");
        }
        //System.out.println("Time/Word Mode: " + settings.get("Time/Word Mode"));
        numWordTimeButtonChanger();

        scanner.close();
    }

    public void allowPunctuationSwitcher() {
        if (isAllowPunctuationClicked) {
            settings.put("Have Punctuation", "true");
        } else {
            settings.put("Have Punctuation", "false");
        }
    }

    public void applySettingsSwitcher() {
        if (isApplySettingsClicked) {
            settings.put("Apply Settings", "true");
        } else {
            settings.put("Apply Settings", "false");
        }
    }

    public void timeWordSwitcher() {
        if (isTimeWordClicked) {
            settings.put("Time/Word Mode", "word");
        } else {
            settings.put("Time/Word Mode", "time");
        }
        timeWordColorSwitcher();
    }

    public void darkMode(boolean bool) {
        if (bool) {
            for (JPanel panel: allPanels) {
                panel.setBackground(Color.decode("#323437"));
            }
            for (JLabel label: allLabels) {
                label.setForeground(Color.white);
            }
            for (JButton button: allButtons) {
                button.setBorderPainted(false);
                button.setOpaque(true);
                button.setFocusable(false);
                button.setBackground(Color.gray);
                button.setForeground(Color.white);
            }
            isInDarkModeCurrently = true;
        } else {
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
                button.setFocusable(false);

                button.setOpaque(false);
            }
            isInDarkModeCurrently = false;
        }
        // add back selected button:
        loadBackColorForButton();
        timeWordColorSwitcher();
    }

    public void timeWordColorSwitcher() {
        if (isInDarkModeCurrently) {
            if (settings.get("Time/Word Mode").equals("word")) {
                wordLabel.setForeground(Color.decode("#F9D092"));
                timeLabel.setForeground(Color.white);
            } else {
                timeLabel.setForeground(Color.decode("#F9D092"));
                wordLabel.setForeground(Color.white);
            }
        } else {
            if (settings.get("Time/Word Mode").equals("word")) {
                wordLabel.setForeground(Color.red);
                timeLabel.setForeground(Color.black);
            } else {
                timeLabel.setForeground(Color.red);
                wordLabel.setForeground(Color.black);
            }
        }
    }

    public void numWordTimeButtonChanger() {
        if (settings.get("Time/Word Mode").equals("time")) {
            number5Button.setText("10");
            number10Button.setText("15");
            number15Button.setText("20");
            number20Button.setText("30");
            anyButton.setVisible(false);
        } else {
            number5Button.setText("10");
            number10Button.setText("15");
            number15Button.setText("20");
            number20Button.setText("30");
            anyButton.setVisible(true);
        }
        System.out.println("Time/Word Mode: " + settings.get("Time/Word Mode"));

        frame.validate();
        frame.repaint();

    }

    public void loadBackColorForButton() {
        for (int i = 0; i  < theMapSizeButtons.size(); i++) {
            //System.out.println("SelectedNumOfWords: " + selectedNumOfWords);
            //System.out.println("action command: " + theMapSizeButtons.get(i).getActionCommand());
            if (selectedNumOfWords == Integer.valueOf(theMapSizeButtons.get(i).getActionCommand())) {
                theMapSizeButtons.get(i).setForeground(Color.decode("#F9D092"));
            }
        }
    }

    public void randomizeMapsSwitcher() {
        if (isRandomizeMapsClicked) {
            settings.put("Randomize Maps", "true");
        } else {
            settings.put("Randomize Maps", "false");
        }

    }

    public JFrame getFrame() {
        return frame;
    }

    public JButton getReturnButton() {
        return returnButton;
    }

    public Map<String,String> getSettings() {
        return settings;
    }

    public int getSelectedNumOfWords() {
        return selectedNumOfWords;
    }

    public JRadioButton getChooseTimeWordRadioButton() {
        return chooseTimeWord;
    }

    public boolean isApplySettingsTrue() {
        return settings.get("Apply Settings").equals("true");
    }

    public static void main(String[] args) throws IOException {
        MapSettingsFrame mapSettingsFrame = new MapSettingsFrame();
        mapSettingsFrame.getFrame().setVisible(true);
        //mapSettingsFrame.darkMode(true);

        //System.out.println(System.getProperty("user.dir"));
    }

    @Override // selected map size happens here
    public void actionPerformed(ActionEvent e) {
        // System.out.println(e.getSource().equals(number5Button));
        for (JButton button: theMapSizeButtons) { // reset all colors for the buttons
            if (isInDarkModeCurrently) {
                button.setForeground(Color.white);
            } else {
                button.setForeground(Color.black);
            }
        }
        if (e.getSource().equals(number5Button)) {
            number5Button.setForeground(Color.decode("#F9D092"));
            selectedNumOfWords = 1;
            settings.put("SelectedNumOfWords", "1");
        } else if (e.getSource().equals(number10Button)) {
            number10Button.setForeground(Color.decode("#F9D092"));
            selectedNumOfWords = 2;
            settings.put("SelectedNumOfWords", "2");
        } else if (e.getSource().equals(number15Button)) {
            number15Button.setForeground(Color.decode("#F9D092"));
            selectedNumOfWords = 3;
            settings.put("SelectedNumOfWords", "3");
        } else if (e.getSource().equals(number20Button)) {
            number20Button.setForeground(Color.decode("#F9D092"));
            selectedNumOfWords = 4;
            settings.put("SelectedNumOfWords", "4");
        } else if (e.getSource().equals(anyButton)) {
            anyButton.setForeground(Color.decode("#F9D092"));
            selectedNumOfWords = -1;
            settings.put("SelectedNumOfWords", "-1");
        }
        try {
            writeToSettings();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println(settings.get("SelectedNumOfWords"));


        
    }
}
