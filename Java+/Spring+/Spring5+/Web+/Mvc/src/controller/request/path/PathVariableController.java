package controller.request.path;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
class PathVariableController {
    static final String ENDPOINT_SINGLE = "/case/upper/single/";
    static final String ENDPOINT_SINGLE_SPLIT = "/case/upper/singleSplit/";
    static final String ENDPOINT_ARRAY = "/case/upper/array/";
    static final String ENDPOINT_LIST = "/case/upper/list/";

    @ResponseBody
    @GetMapping(value = ENDPOINT_SINGLE + "{str}")
    public String upperCaseSingle(@PathVariable String str) {
        return str.toUpperCase();
    }

    @ResponseBody
    @GetMapping(value = ENDPOINT_SINGLE_SPLIT + "{str}")
    public String upperCaseSingleSplit(@PathVariable String str) {
        var elements = str.split(",");
        return String.join("+", elements);
    }

    @ResponseBody
    @GetMapping(value = ENDPOINT_ARRAY + "{strArray}")
    public String upperCaseArray(@PathVariable String[] strArray) {
        return Arrays.stream(strArray).map(String::toUpperCase).collect(Collectors.joining("_"));
    }

    @ResponseBody
    @GetMapping(value = ENDPOINT_LIST + "{strList}")
    public String upperCaseList(@PathVariable List<String> strList) {
        return strList.stream().map(String::toUpperCase).collect(Collectors.joining("-"));
    }

}
