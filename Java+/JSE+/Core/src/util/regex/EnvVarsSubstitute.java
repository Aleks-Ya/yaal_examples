package util.regex;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replace names of environment variables (in format "${var_name}" or "${var_name:def_value}" in a string
 * with their values from a map.
 */
class EnvVarsSubstitute {
    private static final Pattern P = Pattern.compile("\\$\\{(.*)}");

    static String substitute(final String origin, final Map<String, String> envs) {
        if (origin == null) {
            return null;
        }
        String result = origin;
        final Matcher m = P.matcher(origin);
        while (m.find()) {
            final String matchedGroup = m.group();
            final String matchedSubstring = m.group(1);
            final String[] parts = matchedSubstring.split(":", 2);
            final String envVarName;
            final String defaultValue;
            if (parts.length == 1) {
                envVarName = matchedSubstring;
                defaultValue = null;
            } else {
                envVarName = parts[0];
                defaultValue = parts[1];
            }
            if (envVarName.isEmpty()) {
                throw new IllegalArgumentException("Empty environment variable name: " + matchedGroup);
            }
            final String envValue = envs.get(envVarName);
            final String replacer = envValue != null ? envValue : defaultValue;
            if (replacer == null) {
                throw new IllegalArgumentException("Environment variable " + matchedSubstring + " is not found.");
            }
            result = result.replace(matchedGroup, replacer);
        }
        return result;
    }

}
