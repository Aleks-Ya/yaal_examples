package core.net.url;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Remove dot segments (/./ and /../) from URL.
 */
class RemoveDotSegmentsFromUrl {
    static String removeDotSegments(String url) {
        String input = url;
        List<String> output = new ArrayList<>();
        while (!input.isEmpty()) {
            if (input.startsWith("../") || input.startsWith("./")) {
                input = input.substring(input.indexOf("/") + 1);
                continue;
            }
            if (input.startsWith("/./")) {
                input = "/" + input.substring(3);
                continue;
            }
            if (input.startsWith("/../")) {
                input = "/" + input.substring(4);
                output.remove(output.size() - 1);
                continue;
            }
            if (input.startsWith("/..")) {
                input = "/" + input.substring(3);
                output.remove(output.size() - 1);
                continue;
            }
            if (input.startsWith("/.")) {
                input = input.substring(2);
                continue;
            }
            if (input.equals("..") || input.equals(".")) {
                break;
            }

            int slashInd = input.indexOf("/");
            if (slashInd == 0) {
                slashInd = input.indexOf("/", 1);
            }
            if (slashInd == -1) {
                slashInd = input.length();
            }
            output.add(input.substring(0, slashInd));
            input = input.substring(slashInd);
        }
        return output.stream().collect(Collectors.joining());
    }
}
