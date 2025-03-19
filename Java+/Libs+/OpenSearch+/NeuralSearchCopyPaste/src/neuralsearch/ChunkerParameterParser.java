package neuralsearch;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Locale;
import java.util.Map;

public final class ChunkerParameterParser {
    private ChunkerParameterParser() {
    }

    public static String parseString(Map<String, Object> parameters, String fieldName) {
        Object fieldValue = parameters.get(fieldName);
        if (!(fieldValue instanceof String)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "Parameter [%s] must be of %s type", fieldName, String.class.getName()));
        } else if (StringUtils.isEmpty(fieldValue.toString())) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "Parameter [%s] should not be empty.", fieldName));
        } else {
            return fieldValue.toString();
        }
    }

    public static String parseStringWithDefault(Map<String, Object> parameters, String fieldName, String defaultValue) {
        return !parameters.containsKey(fieldName) ? defaultValue : parseString(parameters, fieldName);
    }

    public static int parseInteger(Map<String, Object> parameters, String fieldName) {
        String fieldValueString = parameters.get(fieldName).toString();

        try {
            return NumberUtils.createInteger(fieldValueString);
        } catch (NumberFormatException var4) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "Parameter [%s] must be of %s type", fieldName, Integer.class.getName()));
        }
    }

    public static int parsePositiveInteger(Map<String, Object> parameters, String fieldName) {
        int fieldValueInt = parseInteger(parameters, fieldName);
        if (fieldValueInt <= 0) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "Parameter [%s] must be positive.", fieldName));
        } else {
            return fieldValueInt;
        }
    }

    public static int parsePositiveIntegerWithDefault(Map<String, Object> parameters, String fieldName, Integer defaultValue) {
        return !parameters.containsKey(fieldName) ? defaultValue : parsePositiveInteger(parameters, fieldName);
    }

    public static double parseDouble(Map<String, Object> parameters, String fieldName) {
        String fieldValueString = parameters.get(fieldName).toString();

        try {
            return NumberUtils.createDouble(fieldValueString);
        } catch (NumberFormatException var4) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "Parameter [%s] must be of %s type", fieldName, Double.class.getName()));
        }
    }

    public static double parseDoubleWithDefault(Map<String, Object> parameters, String fieldName, double defaultValue) {
        return !parameters.containsKey(fieldName) ? defaultValue : parseDouble(parameters, fieldName);
    }
}

