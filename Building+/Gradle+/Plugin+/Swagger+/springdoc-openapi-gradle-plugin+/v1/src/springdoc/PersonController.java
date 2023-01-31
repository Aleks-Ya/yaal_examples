package springdoc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
class PersonController {
    private final List<String> persons = new ArrayList<>(List.of("John", "Mary"));

    @GetMapping(value = "/person")
    public String getAll() {
        return persons.toString();
    }
}
