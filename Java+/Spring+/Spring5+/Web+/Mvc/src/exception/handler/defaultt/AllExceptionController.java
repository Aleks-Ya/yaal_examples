package exception.handler.defaultt;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Override DefaultHandlerExceptionResolver for custom processing Standard Spring MVC exceptions.
 */
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
    public void missingServletRequestParameterException(@SuppressWarnings("unused") @RequestParam("abc") String param) {
    }

    @Bean
    public DefaultHandlerExceptionResolver defaultHandlerExceptionResolver() {
        return new MyDefaultHandlerExceptionResolver();
    }

    public static class MyDefaultHandlerExceptionResolver extends DefaultHandlerExceptionResolver {
        @Override
        protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
            var view = new MappingJackson2JsonView();

            var model = new HashMap<String, String>();
            model.put("exception", ex.toString());

            var mav = new ModelAndView(view, model);
            mav.setStatus(HttpStatus.CONFLICT);
            return mav;
        }
    }

}
