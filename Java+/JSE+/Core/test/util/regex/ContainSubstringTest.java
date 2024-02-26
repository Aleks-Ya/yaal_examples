package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Does a String contain a regex substring?
 */
class ContainSubstringTest {
    @Test
    void contains() {
        var text1 = "Parameter {{job.parameters.CompanyName}} is ok";
        var text2 = "Parameter {abc} is ok";
        var p = Pattern.compile("\\{\\{.*}}");
        var contains1 = p.matcher(text1).find();
        assertThat(contains1).isTrue();
        var contains2 = p.matcher(text2).find();
        assertThat(contains2).isFalse();
    }

    @Test
    void containsLookAround() {
        var p = Pattern.compile("\\{\\{(?!job\\.parameters\\.).*}}");
        assertThat(containsLookAround("Param {{job.parameters.CompanyName}} is ok", p)).isFalse();
        assertThat(containsLookAround("Param {{jobXparameters.CompanyName}} is ok", p)).isTrue();
        assertThat(containsLookAround("Param {{job.parametersXCompanyName}} is ok", p)).isTrue();
        assertThat(containsLookAround("Param {{CompanyName}} is ok", p)).isTrue();
        assertThat(containsLookAround("Param {CompanyName} is ok", p)).isFalse();
        assertThat(containsLookAround("Param {{}} is ok", p)).isTrue();
        assertThat(containsLookAround("{{}}", p)).isTrue();
        assertThat(containsLookAround("{{CompanyName}}", p)).isTrue();
    }

    private boolean containsLookAround(String text, Pattern pattern) {
        return pattern.matcher(text).find();
    }
}