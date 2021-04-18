package exception.standard;

import common.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

import static exception.standard.StandardController.MISSING_SERVLET_PARAM_EXCEPTION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Handle exception by annotation {@link org.springframework.web.bind.annotation.ExceptionHandler}
 */
@ContextConfiguration(classes = StandardController.class)
public class StandardControllerTest extends BaseTest {

    @Test
    public void missingServletRequestParameterException() throws Exception {
        mvc.perform(get(MISSING_SERVLET_PARAM_EXCEPTION))
                .andExpect(status().isBadRequest())
        .andExpect(content().string(""));
    }

    @Test
    public void noHandlerFoundException() throws Exception {
        mvc.perform(get("/not_exists"))
                .andExpect(status().is(404))
        .andExpect(content().string(""));
    }
}