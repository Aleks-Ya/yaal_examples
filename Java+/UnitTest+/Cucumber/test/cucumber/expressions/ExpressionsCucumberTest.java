package cucumber.expressions;

import cucumber.BaseCucumberTest;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@SelectClasspathResource("cucumber/expressions")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value =
        "summary,pretty,html:build/reports/cucumber/ExpressionsCucumberTest.html,json:build/reports/cucumber/ExpressionsCucumberTest.json")
class ExpressionsCucumberTest extends BaseCucumberTest {
}
