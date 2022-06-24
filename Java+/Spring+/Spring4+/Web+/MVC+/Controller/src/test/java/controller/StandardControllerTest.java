package controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Handle exception by annotation {@link org.springframework.web.bind.annotation.ExceptionHandler}
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = StandardController.class)
public class StandardControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

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