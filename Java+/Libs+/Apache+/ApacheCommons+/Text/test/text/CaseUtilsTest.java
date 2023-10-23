package text;

import org.apache.commons.text.CaseUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CaseUtilsTest {
    @Test
    void toCamelCase() {
        var camelCase = CaseUtils.toCamelCase("camel_case_string", true, '_');
        assertThat(camelCase).isEqualTo("CamelCaseString");
    }
}
