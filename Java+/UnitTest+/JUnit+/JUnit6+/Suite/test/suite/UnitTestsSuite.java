package suite;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Suite
@SuiteDisplayName("Suite for Unit Tests")
@SelectPackages("suite")
@IncludeClassNamePatterns(".*Test")
class UnitTestsSuite {

}
