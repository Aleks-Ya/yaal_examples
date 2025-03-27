package version;

import java.util.ArrayList;
import java.util.List;

public class SubMain {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        String javaVersion = System.getProperty("java.version");
        System.out.println(javaVersion);
    }
}
