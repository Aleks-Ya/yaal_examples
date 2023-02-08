package cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AgeTest {
    @Autowired
    private MockMvc mvc;
    private ResultActions resultActions;

    @When("the client calls age for person {string}")
    public void the_client_calls_for_person_age(String person) throws Throwable {
        resultActions = mvc.perform(get("/age").param("person", person));
    }

    @Then("^the client receives status code of /age (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        resultActions.andExpect(status().is(statusCode));
    }

    @And("^the client receives client age (-?\\d+)$")
    public void the_client_receives_person_age(Integer age) throws Throwable {
        resultActions.andExpect(content().string(age.toString()));
    }
}