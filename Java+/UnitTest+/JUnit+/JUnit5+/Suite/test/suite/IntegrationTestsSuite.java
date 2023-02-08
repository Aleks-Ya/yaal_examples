package suite;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Suite for Integration Tests")
@SelectPackages("suite")
@IncludeClassNamePatterns(".*IT")
class IntegrationTestsSuite {
}
