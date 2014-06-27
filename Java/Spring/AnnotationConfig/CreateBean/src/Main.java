import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        // todo не дописан
        {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("scan");
            ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
            House house = (House) beanFactory.autowire(House.class, AutowireCapableBeanFactory.AUTOWIRE_NO, true);
            System.out.println(house);
        }

        {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("scan");
            ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

            Object houseObj = beanFactory.createBean(House.class, AutowireCapableBeanFactory.AUTOWIRE_NO, true);
            context.getAutowireCapableBeanFactory().;

            House house = context.getBean(House.class);
            System.out.println(house);

        }

        {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("scan");
            ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
            Object houseObj = beanFactory.createBean(House.class);

            House house = context.getBean(House.class);
            System.out.println(house);
        }
    }
}
