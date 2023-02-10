package cucumber.tags;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagsStepdefs {
    private String person;
    private String message;

    @Given("TAGS my string is {string}")
    public void stringIs(String person) {
        this.person = person;
    }

    @When("TAGS Convert my string")
    public void convertString() {
        message = Converter.convertString(person);
    }

    @Then("TAGS Converted string should be {string}")
    public void convertedShouldBe(String expectedAnswer) {
        assertEquals(expectedAnswer, message);
    }
}

