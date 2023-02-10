package cucumber.tags;

import cucumber.BaseCucumberTest;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.SelectClasspathResource;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@ExcludeTags("Heavy")
@SelectClasspathResource("cucumber/tags")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value =
        "summary,pretty,html:build/reports/cucumber/SmokeTagCucumberTest.html,json:build/reports/cucumber/SmokeTagCucumberTest.json")
class SmokeTagCucumberTest extends BaseCucumberTest {
}
