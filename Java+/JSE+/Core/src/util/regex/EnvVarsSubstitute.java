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

    static String substitute(String origin, Map<String, String> envs) {
        if (origin == null) {
            return null;
        }
        String result = origin;
        Matcher m = P.matcher(origin);
        while (m.find()) {
            String matchedGroup = m.group();
            String matchedSubstring = m.group(1);
            String[] parts = matchedSubstring.split(":");
            String envVarName;
            String defaultValue = null;
            if (parts.length == 1) {
                envVarName = matchedSubstring;
            } else {
                envVarName = parts[0];
                defaultValue = parts[1];
            }
            if (envVarName.isEmpty()) {
                throw new IllegalArgumentException("Empty environment variable name: " + matchedGroup);
            }
            String envValue = envs.get(envVarName);
            String replacer = envValue != null ? envValue : defaultValue;
            if (replacer == null) {
                throw new IllegalArgumentException("Environment variable " + matchedSubstring + " is not found.");
            }
            result = result.replace(matchedGroup, replacer);
        }
        return result;
    }

}
