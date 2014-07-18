import org.apache.commons.collections4.CollectionUtils;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println(CollectionUtils.isNotEmpty(new ArrayList(0)));
    }
}
