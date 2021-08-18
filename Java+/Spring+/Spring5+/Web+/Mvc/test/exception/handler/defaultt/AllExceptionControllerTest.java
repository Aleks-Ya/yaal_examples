package exception.handler.defaultt;

import common.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

import static exception.handler.defaultt.AllExceptionController.FILE_NOT_FOUND_EXCEPTION;
import static exception.handler.defaultt.AllExceptionController.MISSING_SERVLET_PARAM_EXCEPTION;
import static exception.handler.defaultt.AllExceptionController.NUMBER_FORMAT_EXCEPTION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = AllExceptionController.class)
class AllExceptionControllerTest extends BaseTest {

    private static final int HTTP_STATUS = 409;

    @Test
    void fileNotFoundException() throws Exception {
        mvc.perform(get(FILE_NOT_FOUND_EXCEPTION))
                .andExpect(status().is(HTTP_STATUS))
                .andExpect(content().string("{\"exception\":\"java.io.FileNotFoundException: File Not Found Exception\"}"));
    }


    @Test
    void numberFormatException() throws Exception {
        mvc.perform(get(NUMBER_FORMAT_EXCEPTION))
                .andExpect(status().is(HTTP_STATUS))
                .andExpect(content().string("{\"exception\":\"java.lang.NumberFormatException: Number Format Exception\"}"));
    }

    @Test
    void missingServletRequestParameterException() throws Exception {
        mvc.perform(get(MISSING_SERVLET_PARAM_EXCEPTION))
                .andExpect(status().is(HTTP_STATUS))
                .andExpect(content().string("{\"exception\":\"org.springframework.web.bind.MissingServletRequestParameterException: Required request parameter 'abc' for method parameter type String is not present\"}"));
    }

    /**
     * We can't process NoHandlerFoundException with DefaultHandlerExceptionResolver.
     */
    @Test
    void noHandlerFoundException() throws Exception {
        mvc.perform(get("/not_exists"))
                .andExpect(status().is(404))
                .andExpect(content().string(""));
    }

}
