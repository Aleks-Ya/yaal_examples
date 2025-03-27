package version;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        String javaVersion = System.getProperty("java.version");
        System.out.println(javaVersion);
        Class.forName("java.net.http.HttpClient");

        //Added in Java 9:
        //java.net.http.HttpClient

        //Added in Java 16:
        //java.lang.Record
    }
}
