package SpeedType;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Stat {
    
    private String title;
    private Date currentDate;
    private SimpleDateFormat timeFormat;
    private int numOfGames;
    private double avgWpmRaw;
    private double avgWpmActual;
    private double avgAccuracy;

    public Stat() {
        currentDate = new Date();
        timeFormat = new SimpleDateFormat("MM-dd-yy");

        title = timeFormat.format(currentDate);
        String[] temp = title.split("-");
        title = "";
        title += temp[0] + "/" + temp[1];
        
    }

    public String getTitle() {
        return title;
    }

    public void setStats(int numOfGames, double avgRaw, double avgActual, double accuracy) { // pull stats from player class
        this.numOfGames = numOfGames;
        avgWpmRaw = avgRaw;
        avgWpmActual = avgActual;
        avgAccuracy = accuracy;

    }

    public void makeSave() throws IOException {
        String fileName = System.getProperty("user.dir") + "/SpeedType/Saves/" + "Save " + timeFormat.format(currentDate) + ".txt";
        // System.out.println(fileName);
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write("Number of Games: " + numOfGames + "\n");
        fileWriter.write("Average WPM (Raw): " + avgWpmRaw + "\n");
        fileWriter.write("Average WPM (Actual): " + avgWpmActual + "\n");
        fileWriter.write("Average Accuracy: " + avgAccuracy + "\n");

        fileWriter.close();

    }

    public void makeSaveCustom(Stat stat) throws IOException {
        String fileName = System.getProperty("user.dir") + "/SpeedType/Saves/" + "Save " + timeFormat.format(currentDate) + ".txt";
        // System.out.println(fileName);

        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write("Number of Games: " + stat.getGames() + "\n");
        fileWriter.write("Average WPM (Raw): " + stat.getAvgWpmRaw() + "\n");
        fileWriter.write("Average WPM (Actual): " + stat.getAvgWpmActual() + "\n");
        fileWriter.write("Average Accuracy: " + stat.getAvgAccuracy() + "\n");

        fileWriter.close();
    }

    public int getGames() {
        return numOfGames;
    }

    public double getAvgWpmRaw() {
        return avgWpmRaw;
    }

    public double getAvgWpmActual() {
        return avgWpmActual;
    }

    public double getAvgAccuracy() {
        return avgAccuracy;
    }

    public static void main(String[] args) throws IOException {
        Stat stat = new Stat();
        SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd");
        //System.out.println(timeFormat.format(stat.currentDate));
        /* 
        FileWriter fileWriter = new FileWriter("Save1.txt");
        fileWriter.write("\n hello");
        

        fileWriter.close();
        */
        stat.setStats(1, 80, 80, 100);
        stat.makeSave();
    }
}
