package exception.standard;

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

import static exception.standard.StandardController.MISSING_SERVLET_PARAM_EXCEPTION;
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