import org.apache.commons.lang3.StringUtils;
import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        //Сравнение строк (безопасно с null)
        out.println(StringUtils.equalsIgnoreCase(null, ""));
        
        //Пустая строка
        out.println(StringUtils.isBlank(null));
    }
}
