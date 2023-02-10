package cucumber.expressions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CucumberExpressionsStepdefs {
    private String person;
    private String message;

    @Given("CUCUMBER my string is {string}")
    public void stringIs(String person) {
        this.person = person;
    }

    @When("CUCUMBER Convert my string")
    public void convertString() {
        message = Converter.convertString(person);
    }

    @Then("CUCUMBER Converted string should be {string}")
    public void convertedShouldBe(String expectedAnswer) {
        assertEquals(expectedAnswer, message);
    }
}

