package scan.person;

import org.springframework.stereotype.Service;

@Service("zadornov1")
public class Zadornov implements IPerson {
    @Override
    public String getFio() {
        return "Оригинальный Задорнов М.";
    }

    @Override
    public String toString() {
        return getFio();
    }
}
