package controller.request.path;

import common.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = PathVariableController.class)
class PathVariableControllerTest extends BaseTest {
    @Test
    void pathVariable() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(PathVariableController.ENDPOINT + "abc"))
                .andExpect(status().isOk())
                .andExpect(content().string("ABC"));
    }
}