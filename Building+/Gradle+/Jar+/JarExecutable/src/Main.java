import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("Application context: " + new AnnotationConfigApplicationContext());
    }
}