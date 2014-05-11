import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import scan.Constants;
import scan.OtherBean;
import scan.StaticMethods;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("scan");
        System.out.println(context.getBean(Constants.class));
        System.out.println(context.getBean(OtherBean.class));
        System.out.println(context.getBean(StaticMethods.class));
    }
}
