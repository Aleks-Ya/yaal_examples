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
    void single() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(PathVariableController.ENDPOINT_SINGLE + "abc"))
                .andExpect(status().isOk())
                .andExpect(content().string("ABC"));
    }

    @Test
    void singleSplit() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(PathVariableController.ENDPOINT_SINGLE_SPLIT + "abc,efg,xyz"))
                .andExpect(status().isOk())
                .andExpect(content().string("abc+efg+xyz"));
    }

    @Test
    void array_NotWork() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(PathVariableController.ENDPOINT_ARRAY + "abc,efg,xyz"))
                .andExpect(status().isOk())
                .andExpect(content().string("ABC,EFG,XYZ"));//Should be "ABC_EFG_XYZ"
    }

    @Test
    void list_NotWork() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(PathVariableController.ENDPOINT_LIST + "abc,efg,xyz"))
                .andExpect(status().isOk())
                .andExpect(content().string("ABC,EFG,XYZ"));//Should be "ABC-EFG-XYZ"
    }
}