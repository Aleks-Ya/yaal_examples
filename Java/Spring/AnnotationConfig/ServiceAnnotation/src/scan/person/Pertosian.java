package scan.person;

import org.springframework.stereotype.Service;

@Service
public class Pertosian extends Artist {
    @Override
    public String getFio() {
        return "Е. Петросян";
    }
}
