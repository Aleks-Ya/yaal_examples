package exception.handler;

import common.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

import static exception.handler.ResponseStatusController.RESPONSE_STATUS_MAPPING;
import static exception.handler.ResponseStatusController.RESPONSE_STATUS_WITH_BODY_CONTENT;
import static exception.handler.ResponseStatusController.RESPONSE_STATUS_WITH_BODY_MAPPING;
import static exception.handler.ResponseStatusController.RESPONSE_STATUS_WITH_BODY_WITH_EXCEPTION_MAPPING;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = ResponseStatusController.class)
public class ResponseStatusControllerTest extends BaseTest {

    @Test
    public void responseStatus() throws Exception {
        mvc.perform(get(RESPONSE_STATUS_MAPPING))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void responseStatusWithBody() throws Exception {
        mvc.perform(get(RESPONSE_STATUS_WITH_BODY_MAPPING))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(RESPONSE_STATUS_WITH_BODY_CONTENT));
    }

    @Test
    public void responseStatusWithBodyWithException() throws Exception {
        mvc.perform(get(RESPONSE_STATUS_WITH_BODY_WITH_EXCEPTION_MAPPING))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        "java.lang.ArrayIndexOutOfBoundsException: " +
                                "Response Status With Body With Exception"));
    }

}
