package controller.request.param;

import common.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = RequestParamController.class)
class RequestParamControllerTest extends BaseTest {
    @Test
    void pathVariable() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(RequestParamController.ENDPOINT).param("str", "abc"))
                .andExpect(status().isOk())
                .andExpect(content().string("ABC"));
    }
}