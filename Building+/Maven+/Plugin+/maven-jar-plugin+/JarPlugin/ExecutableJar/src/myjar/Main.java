package myjar;

public class Main {
    public static void main(String[] args) {
        System.out.println("myjar.Main: Hello!");

        //Not working from JAR (should use maven-assembly-plugin or maven-shade-plugin)
        org.slf4j.LoggerFactory.getLogger(Main.class).info("Logger is configured");
    }
}
