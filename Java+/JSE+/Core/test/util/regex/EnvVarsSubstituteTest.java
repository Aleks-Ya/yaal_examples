package util.regex;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnvVarsSubstituteTest {
    private final Map<String, String> vars = new HashMap<>();

    @Test
    public void shouldReturnNullIfOriginStringIsNull() {
        assertNull(EnvVarsSubstitute.substitute(null, vars));
    }

    @Test
    public void shouldReturnTheSameStringIfNoVariablesExistInTheOriginString() {
        String content = "text: abc";
        String act1 = EnvVarsSubstitute.substitute(content, vars);
        String act2 = EnvVarsSubstitute.substitute(content, null);

        assertThat(act1, equalTo(content));
        assertThat(act2, equalTo(content));
    }

    @Test
    public void shouldReplaceVariableIfNoDefaultValueSpecified() {
        String content = "text: ${MY_VAR}_abc";
        vars.put("MY_VAR", "MY VALUE");
        String act = EnvVarsSubstitute.substitute(content, vars);
        assertThat(act, equalTo("text: MY VALUE_abc"));
    }

    @Test
    public void shouldUseDefaultValueIfVariableIsNotPresentInMap() {
        String content = "text: ${MY_VAR:100}_abc";
        String act = EnvVarsSubstitute.substitute(content, vars);
        assertThat(act, equalTo("text: 100_abc"));
    }

    @Test
    public void shouldUseFirstSemicolonAsDefaultValueDelimiter() {
        String content = "text: ${MY_VAR:jdbc:phoenix}_abc";
        String act = EnvVarsSubstitute.substitute(content, vars);
        assertThat(act, equalTo("text: jdbc:phoenix_abc"));
    }

    @Test
    public void emptyVarName() {
        assertThrows(IllegalArgumentException.class, () -> EnvVarsSubstitute.substitute("text: ${}", vars));
    }

    @Test
    public void shouldReternEnvVariableIfEmptyDefaultValue() {
        vars.put("abc", "123");
        EnvVarsSubstitute.substitute("text: ${abc:}", vars);
    }

    @Test
    public void varNotFound() {
        assertThrows(IllegalArgumentException.class, () -> EnvVarsSubstitute.substitute("text: ${not_exists}", vars));
    }
}