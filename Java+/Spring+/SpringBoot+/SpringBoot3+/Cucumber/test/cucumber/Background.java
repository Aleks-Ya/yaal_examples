package cucumber;

import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class Background {
    @Autowired
    private MockMvc mvc;

    @Given("create person {string} {int}")
    public void createPersonJohn(String name, Integer age) throws Exception {
        mvc.perform(post("/person").param("name", name).param("age", age.toString()));
    }
}
