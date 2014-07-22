import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class SubClass{
    public static void main(String[] args) throws IOException {
        BufferedReader bis = new BufferedReader(new InputStreamReader(SubClass.class.getResourceAsStream("message.txt")));
        String line;
        while((line = bis.readLine()) != null) {
			System.out.println(line);
		}
		bis.close();
    }
}