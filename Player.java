package SpeedType;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Player {

    private int gamesPlayed;
    private double totalAccuracy;
    private double totalActual;
    private double totalRaw;
    private Date currentDate = new Date();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd");
    public Player() {
        gamesPlayed = 0;
        totalAccuracy = 0;
        totalActual = 0;
        totalRaw = 0;
    }

    public void addGame() {
        gamesPlayed++;
    }

    public void addGameByAmount(int games) {
        gamesPlayed += games;
    }

    public int getGames() {
        return gamesPlayed;
    }
    
    public double getRaw() {
        return totalRaw;
    }

    public double getActual() {
        return totalActual;
    }

    public double getAccuracy() {
        return totalAccuracy;
    }

    public void addAccuracy(double num) {
        totalAccuracy += num;
    }

    public void addActual(double num) {
        totalActual += num;
    }

    public void addRaw(double num) {
        totalRaw += num;
    }

    public double avgAccuracy() {
        return Math.round(totalAccuracy / gamesPlayed);
        // return Math.round( (totalAccuracy/ (gamesPlayed)) * 1000.0 ) / 10.0;
    }

    public double avgActual() {
        return Math.round( (totalActual/ (gamesPlayed)) * 1000.0 ) / 1000.0;
    }

    public double avgRaw() {
        return Math.round( (totalRaw/ (gamesPlayed)) * 1000.0 ) / 1000.0;
    }
    
    public void readStats() throws FileNotFoundException { // read stats from stat file whenever user launches game again
        String fileName = "Saves/" + "Save " + timeFormat.format(currentDate) + ".txt";
        File file = new File(fileName);
        Scanner scanner = null;
        boolean foundFile = false;
        try {
            scanner = new Scanner(file);
            foundFile = true;
        } catch (FileNotFoundException e) {
            foundFile = false;
        }

        if (!foundFile) {
            return;
        }
        else {
            while (scanner.hasNextLine()) {
                String currentLine = "";
                String[] tempCheck = {};
                currentLine = scanner.nextLine();
                tempCheck = currentLine.split(" ");
                if (currentLine.startsWith("Number of Games: ")) {
                    gamesPlayed = Integer.valueOf(tempCheck[tempCheck.length-1]);
                }
                if (currentLine.startsWith("Average WPM (Raw): ")) {
                    totalRaw = Double.parseDouble(tempCheck[tempCheck.length-1]) * gamesPlayed;
                }
                if (currentLine.startsWith("Average WPM (Actual): ")) {
                    totalActual = Double.parseDouble(tempCheck[tempCheck.length-1]) * gamesPlayed;
                }
                if (currentLine.startsWith("Average Accuracy: ")) {
                    totalAccuracy = Double.parseDouble(tempCheck[tempCheck.length-1]) * gamesPlayed;
                }
            }
        }
        scanner.close();
    }
    
    
    public static void main(String[] args) throws FileNotFoundException {
        Player player = new Player();

        player.readStats();

        System.out.println(player.gamesPlayed + " " + player.avgRaw() + " " + player.avgActual() + " " + player.avgAccuracy());
    }
}
