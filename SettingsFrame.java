package SpeedType;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class SettingsFrame {
    
    /*  settings:
    dark/light mode
    number of words
    
    */  

    private JFrame frame;
    private Map<String, String> settings;
    private ArrayList<JPanel> panelArray;
    private ArrayList<JLabel> AllLabels;
    private ArrayList<JButton> AllButtons;
    private JButton returnButton;

    private boolean isLightModeClicked;
    private boolean isCarrySaveClicked;
    private boolean isSettingsLoadClicked;
    
    private static JRadioButton darkLightRadioButton;
    private static JRadioButton carrySaveRadioButton;
    private static JRadioButton loadSettingsRadioButton;

    public SettingsFrame() throws IOException {
        settings = new HashMap<String, String>();
        AllLabels = new ArrayList<JLabel>();
        AllButtons = new ArrayList<JButton>();

        settings.put("dark/light mode", "light");
        settings.put("carry save", "false");
        //settings.put("number of words", "NP"); //NP = no preference (will be in textsettings)


        frame = new JFrame();
        frame.setLayout(new GridLayout(10,2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);

        // setup panels
        panelArray = new ArrayList<JPanel>();

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Settings");
        AllLabels.add(title);

        JPanel darkLightModePanel = new JPanel();

        JLabel darkLightLabel = new JLabel("Dark Mode");
        AllLabels.add(darkLightLabel);
        darkLightRadioButton = new JRadioButton();

        returnButton = new JButton("Return");
        returnButton.setPreferredSize(new Dimension(85,40));
        returnButton.setContentAreaFilled(false);
        returnButton.setOpaque(true);
        returnButton.setFocusable(false);
        AllButtons.add(returnButton);

        JPanel panel2 = new JPanel();
        JPanel panel5 = new JPanel();
        JPanel carrySavePanel = new JPanel();

        JLabel carrySaveLabel = new JLabel("Carry save to today's stats");
        AllLabels.add(carrySaveLabel);
        carrySaveRadioButton = new JRadioButton();

        JPanel panel6 = new JPanel();
        JPanel panel7 = new JPanel();
        JPanel loadSettingsOnStartPanel = new JPanel();

        JLabel loadSettingsLabel = new JLabel("Load Settings on Startup");
        AllLabels.add(loadSettingsLabel);
        loadSettingsRadioButton = new JRadioButton();

        JPanel panel9 = new JPanel();
        JPanel returnButtonPanel = new JPanel();

        panelArray.add(titlePanel);
        panelArray.add(panel2);
        panelArray.add(darkLightModePanel);
        panelArray.add(carrySavePanel);
        panelArray.add(panel5);
        panelArray.add(panel6);
        panelArray.add(panel7);
        panelArray.add(loadSettingsOnStartPanel);
        panelArray.add(panel9);
        panelArray.add(returnButtonPanel);

        // ActionListeners:

        // keep track of clicked values
        isLightModeClicked = false;

        darkLightRadioButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                isLightModeClicked = !isLightModeClicked;
                lightModeSwitcher();

                try {
                    writeToSettings();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
            
        });

        isSettingsLoadClicked = false;

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

        isCarrySaveClicked = false;

        carrySaveRadioButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                isCarrySaveClicked = !isCarrySaveClicked;
                carrySaveSwitcher();

                try {
                    writeToSettings();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
            
        });

        // add components to panels
        titlePanel.add(title);
        darkLightModePanel.add(darkLightLabel);
        darkLightModePanel.add(darkLightRadioButton);

        carrySavePanel.add(carrySaveLabel);
        carrySavePanel.add(carrySaveRadioButton);

        loadSettingsOnStartPanel.add(loadSettingsLabel);
        loadSettingsOnStartPanel.add(loadSettingsRadioButton);

        returnButtonPanel.add(returnButton);

        // add panels to frame
        for (JPanel panel: panelArray) {
            frame.add(panel);
        }

        loadSettingsOnStart();
        writeToSettings();
        
    }

    public JFrame getFrame() {
        return frame;
    }

    public Map<String, String> getSettings() {
        return settings;
    }

    public JButton getReturnButton() {
        return returnButton;
    }


    public void writeToSettings() throws IOException { 
        FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + "/SpeedType/settings.txt");
        String loadSettingsString;
        String darkMode;
        String carrySave;
        if (isSettingsLoadClicked) {
            loadSettingsString = "true";
        } else {
            loadSettingsString = "false";
        }

        if (isLightModeClicked) {
            darkMode = "true";
        } else {
            darkMode = "false";
        }

        if (isCarrySaveClicked) {
            carrySave = "true";
        } else {
            carrySave = "false";
        }

        fileWriter.write("Load on Startup: " + loadSettingsString + "\n");
        fileWriter.write("\n");
        fileWriter.write("darkMode: " + darkMode + "\n");
        fileWriter.write("carrySave: " + carrySave);


        fileWriter.close();
    }

    public void loadSettingsOnStart() throws FileNotFoundException {
        File file = new File(System.getProperty("user.dir") + "/SpeedType/settings.txt");
        Scanner scanner = new Scanner(file);

        String check = scanner.nextLine();
        scanner.nextLine(); // skip space
        System.out.println(check);
        if (check.split(" ")[check.split(" ").length-1].equals("true")) { // see if we should load this
            isSettingsLoadClicked = true;
            loadSettingsRadioButton.setSelected(true);
            System.out.println("I say yes");
            check = scanner.nextLine();

            if (check.split(" ")[check.split(" ").length-1].equals("true")) { // check dark mode
                isLightModeClicked = true;
                darkLightRadioButton.setSelected(true);
                lightModeSwitcher();
            }

            check = scanner.nextLine();
            System.out.println(check);

            if (check.split(" ")[check.split(" ").length-1].equals("true")) { // check carry Save
                isCarrySaveClicked = true;
                carrySaveRadioButton.setSelected(true);
                carrySaveSwitcher();
            }


        } else {
            System.out.println("hey what do you know!");
        }

        scanner.close();
    }

    public void lightModeSwitcher() {
        if (isLightModeClicked) { // switch to dark
            settings.put("dark/light mode", "dark");
            for (JPanel panel: panelArray) {
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
        } else { // switch to light
            settings.put("dark/light mode", "light");
            for (JPanel panel: panelArray) {
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

    public void carrySaveSwitcher() {
        if (isCarrySaveClicked) {
            settings.put("carry save", "true");
        } else {
            settings.put("carry save", "false");
        }
    }

    public static void main(String[] args) throws IOException {
        SettingsFrame settingsFrame = new SettingsFrame();
        settingsFrame.getFrame().setVisible(true);
    }
}
