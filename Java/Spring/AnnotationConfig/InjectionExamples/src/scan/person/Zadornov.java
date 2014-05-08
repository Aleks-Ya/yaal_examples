package scan.person;

import org.springframework.stereotype.Service;

@Service
public class Zadornov implements IPerson {
    @Override
    public String getFio() {
        return "Задорнов М.";
    }

    @Override
    public String toString() {
        return getFio();
    }
}
