package application.exception.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Controller
class AllExceptionController {

    static final String FILE_NOT_FOUND_EXCEPTION = "/file_not_found_exception";
    static final String MISSING_SERVLET_PARAM_EXCEPTION = "/missing_servlet_request_parameter_exception";
    static final String NUMBER_FORMAT_EXCEPTION = "/number_format_exception";

    @RequestMapping(FILE_NOT_FOUND_EXCEPTION)
    @ResponseBody
    public String fileNotFoundException() throws FileNotFoundException {
        throw new FileNotFoundException("File Not Found Exception");
    }

    @RequestMapping(NUMBER_FORMAT_EXCEPTION)
    @ResponseBody
    public String numberFormatException() {
        throw new NumberFormatException("Number Format Exception");
    }

    @RequestMapping(MISSING_SERVLET_PARAM_EXCEPTION)
    public void missingServletRequestParameterException(
            @RequestParam("abc") String param
    ) {
    }

//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "blah")
//    @ResponseBody
//    public String handler3(ArrayIndexOutOfBoundsException e) {
//        return e.toString();
//    }

    @Bean
    public DefaultHandlerExceptionResolver defaultHandlerExceptionResolver() {
        return new MyDefaultHandlerExceptionResolver();
    }

    public static class MyDefaultHandlerExceptionResolver extends DefaultHandlerExceptionResolver {
//        @Override
//        public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//            ModelAndView mav = new ModelAndView();
//            mav.setStatus(HttpStatus.CONFLICT);
//            View view = new MappingJackson2JsonView();
//            mav.setView(view);
//            return mav;
//
//        }

        @Override
        protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
            MappingJackson2JsonView view = new MappingJackson2JsonView();

            Map<String, String> model = new HashMap<>();
            model.put("exception", ex.toString());

            ModelAndView mav = new ModelAndView(view, model);
            mav.setStatus(HttpStatus.CONFLICT);
            return mav;
        }
    }

}
