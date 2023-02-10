package cucumber.tags;

import cucumber.BaseCucumberTest;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasspathResource;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@IncludeTags("Heavy")
@SelectClasspathResource("cucumber/tags")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value =
        "summary,pretty,html:build/reports/cucumber/HeavyTagCucumberTest.html,json:build/reports/cucumber/HeavyTagCucumberTest.json")
class HeavyTagCucumberTest extends BaseCucumberTest {
}
