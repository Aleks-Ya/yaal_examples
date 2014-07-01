import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import scan.Book;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("scan");
        {
            //Получаем бин по классу (без использования имени бина)
            Book book = context.getBean(Book.class);
            System.out.println(book);
        }
        {
            //Получаем бин по имени
            Book book = context.getBean("makeBook", Book.class);
            System.out.println(book);
        }
    }
}
