package cucumber.expressions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegularExpressionsStepdefs {
    private String person;
    private String message;

    @Given("^REGULAR my string is (.+)$")
    public void stringIs(String person) {
        this.person = person;
    }

    @When("^REGULAR Convert my string$")
    public void convertString() {
        message = Converter.convertString(person);
    }

    @Then("^REGULAR Converted string should be (.+)$")
    public void convertedShouldBe(String expectedAnswer) {
        assertEquals(expectedAnswer, message);
    }
}

