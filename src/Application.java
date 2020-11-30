import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input path to input.txt");
        String path = reader.readLine();
        Timetable tb = new Timetable();
        tb.readData(path);
        tb.modification();
        tb.writeData();
    }
}
