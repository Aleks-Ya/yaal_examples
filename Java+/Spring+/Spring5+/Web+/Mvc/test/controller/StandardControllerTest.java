package controller;

import common.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Handle exception by annotation {@link org.springframework.web.bind.annotation.ExceptionHandler}
 */
@ContextConfiguration(classes = StandardController.class)
class StandardControllerTest extends BaseTest {

    @Test
    void requestParam() throws Exception {
        mvc.perform(get("/requestParam"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void model() throws Exception {
        mvc.perform(get("/model"))
                .andExpect(status().isOk())
                .andExpect(content().string("helloWorld"));
    }
}