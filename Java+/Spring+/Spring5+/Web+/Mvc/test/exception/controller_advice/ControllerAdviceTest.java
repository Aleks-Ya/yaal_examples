package exception.controller_advice;

import common.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {Config.class, ControllerAdviceComponent.class, TheController.class})
public class ControllerAdviceTest extends BaseTest {

    @Test
    public void responseStatus() throws Exception {
        mvc.perform(get(TheController.ENDPOINT))
                .andExpect(content().string(TheController.EXCEPTION_MESSAGE))
                .andExpect(status().is(HttpStatus.I_AM_A_TEAPOT.value()));
    }
}
