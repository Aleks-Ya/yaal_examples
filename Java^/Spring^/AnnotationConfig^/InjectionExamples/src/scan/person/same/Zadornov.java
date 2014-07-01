package scan.person.same;

import org.springframework.stereotype.Component;
import scan.person.IPerson;

@Component("zadornov2")
public class Zadornov implements IPerson {
    @Override
    public String getFio() {
        return "Двойник Задорнова М.";
    }

    @Override
    public String toString() {
        return getFio();
    }
}
