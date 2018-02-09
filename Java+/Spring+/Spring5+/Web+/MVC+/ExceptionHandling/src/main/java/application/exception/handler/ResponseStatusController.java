package application.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;

@Controller
class ResponseStatusController {

    static final String RESPONSE_STATUS_MAPPING = "/response_status";

    static final String RESPONSE_STATUS_WITH_BODY_MAPPING = "/response_status_with_body";
    static final String RESPONSE_STATUS_WITH_BODY_CONTENT = "Body of Response Status With Body";

    static final String RESPONSE_STATUS_WITH_BODY_WITH_EXCEPTION_MAPPING = "/response_status_with_body_with_exception";

    @RequestMapping(RESPONSE_STATUS_MAPPING)
    @ResponseBody
    public String responseStatus() throws FileNotFoundException {
        throw new FileNotFoundException("Response Status");
    }

    @RequestMapping(RESPONSE_STATUS_WITH_BODY_MAPPING)
    @ResponseBody
    public String responseStatusWithBody() {
        throw new NumberFormatException("Response Status With Body");
    }

    @RequestMapping(RESPONSE_STATUS_WITH_BODY_WITH_EXCEPTION_MAPPING)
    @ResponseBody
    public String getResponseStatusWithBodyWithException() {
        throw new ArrayIndexOutOfBoundsException("Response Status With Body With Exception");
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "blah")
    public void handler() {
        System.out.println("handler processed");
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "blah")
    @ResponseBody
    public String handler2() {
        return RESPONSE_STATUS_WITH_BODY_CONTENT;
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "blah")
    @ResponseBody
    public String handler3(ArrayIndexOutOfBoundsException e) {
        return e.toString();
    }

}
