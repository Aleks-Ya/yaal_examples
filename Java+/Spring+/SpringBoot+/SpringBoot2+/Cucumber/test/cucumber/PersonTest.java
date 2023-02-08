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

public class PersonTest {
    @Autowired
    private MockMvc mvc;
    private ResultActions resultActions;

    @When("the client calls person name")
    public void the_client_issues_GET_person() throws Throwable {
        resultActions = mvc.perform(get("/person"));
    }

    @Then("the client receives status code {int}")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        resultActions.andExpect(status().is(statusCode));
    }

    @And("^the client receives client name (.+)$")
    public void the_client_receives_server_version_body(String name) throws Throwable {
        resultActions.andExpect(content().string(name));
    }
}