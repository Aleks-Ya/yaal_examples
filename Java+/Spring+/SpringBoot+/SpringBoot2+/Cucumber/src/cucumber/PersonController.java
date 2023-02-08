package cucumber;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
class PersonController {
    private final Map<String, Integer> persons = new HashMap<>();

    @PostMapping("/person")
    @ResponseBody
    public void createPerson(@RequestParam String name, @RequestParam Integer age) {
        persons.put(name, age);
    }

    @GetMapping("/person")
    @ResponseBody
    public String person() {
        return "John";
    }

    @GetMapping("/age")
    @ResponseBody
    public String age(String person) {
        return String.valueOf(persons.getOrDefault(person, -1));
    }
}
