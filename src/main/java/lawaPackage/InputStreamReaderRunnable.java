package lawaPackage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//import com.twitter.chill.Base64.InputStream;

public class InputStreamReaderRunnable implements Runnable {

    private BufferedReader reader;

    private String name;


    public InputStreamReaderRunnable(java.io.InputStream inputStream, String name2) {
        // TODO Auto-generated constructor stub
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.name = name2;
    }

    public void run() {
        System.out.println("InputStream " + name + ":");
        try {
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
