package util.regex;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EnvVarsSubstituteTest {
    private final Map<String, String> vars = new HashMap<>();

    @Test
    void shouldReturnNullIfOriginStringIsNull() {
        assertThat(EnvVarsSubstitute.substitute(null, vars)).isNull();
    }

    @Test
    void shouldReturnTheSameStringIfNoVariablesExistInTheOriginString() {
        var content = "text: abc";
        var act1 = EnvVarsSubstitute.substitute(content, vars);
        var act2 = EnvVarsSubstitute.substitute(content, null);

        assertThat(act1).isEqualTo(content);
        assertThat(act2).isEqualTo(content);
    }

    @Test
    void shouldReplaceVariableIfNoDefaultValueSpecified() {
        var content = "text: ${MY_VAR}_abc";
        vars.put("MY_VAR", "MY VALUE");
        var act = EnvVarsSubstitute.substitute(content, vars);
        assertThat(act).isEqualTo("text: MY VALUE_abc");
    }

    @Test
    void shouldUseDefaultValueIfVariableIsNotPresentInMap() {
        var content = "text: ${MY_VAR:100}_abc";
        var act = EnvVarsSubstitute.substitute(content, vars);
        assertThat(act).isEqualTo("text: 100_abc");
    }

    @Test
    void shouldUseFirstSemicolonAsDefaultValueDelimiter() {
        var content = "text: ${MY_VAR:jdbc:phoenix}_abc";
        var act = EnvVarsSubstitute.substitute(content, vars);
        assertThat(act).isEqualTo("text: jdbc:phoenix_abc");
    }

    @Test
    void emptyVarName() {
        assertThatThrownBy(() -> EnvVarsSubstitute.substitute("text: ${}", vars))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldReternEnvVariableIfEmptyDefaultValue() {
        vars.put("abc", "123");
        EnvVarsSubstitute.substitute("text: ${abc:}", vars);
    }

    @Test
    void varNotFound() {
        assertThatThrownBy(() -> EnvVarsSubstitute.substitute("text: ${not_exists}", vars))
                .isInstanceOf(IllegalArgumentException.class);
    }
}