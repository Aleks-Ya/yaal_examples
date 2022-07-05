package ru.yaal.spring.mvc.controller.argument;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Autowire parameters of controller's methods.
 */
@Controller
@ResponseBody
public class ArgumentController {

    @RequestMapping(value = "/http")
    public String http(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        int length = request.getContentLength();
        String encoding = response.getCharacterEncoding();
        int maxInactiveInterval = session.getMaxInactiveInterval();

        return String.format("Content length: %d, Max inactive interval: %d, Encoding: %s", length, maxInactiveInterval,
                encoding);
    }

    @RequestMapping(value = "/request_param")
    public String requestParam(@RequestParam("code") String code) {
        return "Request param 'code': " + code;
    }

    @RequestMapping(value = "/all_request_params")
    public String allRequestParams(@RequestParam Map<String, String> params) {
        return "All request params: " + params;
    }

    @RequestMapping(value = "/path_variable/{operation}/{id}")
    public String pathVariable(@PathVariable("operation") String operationName, @PathVariable int id) {
        return String.format("Operation: %s, Id: %d", operationName, id);
    }
}
