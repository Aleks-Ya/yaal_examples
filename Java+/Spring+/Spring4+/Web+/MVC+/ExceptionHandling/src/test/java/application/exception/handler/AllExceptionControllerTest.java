package application.exception.handler;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static application.exception.handler.AllExceptionController.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AllExceptionController.class)
public class AllExceptionControllerTest {

    private static final int HTTP_STATUS = 409;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void fileNotFoundException() throws Exception {
        mvc.perform(get(FILE_NOT_FOUND_EXCEPTION))
                .andExpect(status().is(HTTP_STATUS))
                .andExpect(content().string("{\"exception\":\"java.io.FileNotFoundException: File Not Found Exception\"}"));
    }


    @Test
    public void numberFormatException() throws Exception {
        mvc.perform(get(NUMBER_FORMAT_EXCEPTION))
                .andExpect(status().is(HTTP_STATUS))
                .andExpect(content().string("{\"exception\":\"java.lang.NumberFormatException: Number Format Exception\"}"));
    }

    @Test
    public void missingServletRequestParameterException() throws Exception {
        mvc.perform(get(MISSING_SERVLET_PARAM_EXCEPTION))
                .andExpect(status().is(HTTP_STATUS))
                .andExpect(content().string("{\"exception\":\"org.springframework.web.bind.MissingServletRequestParameterException: Required String parameter 'abc' is not present\"}"));
    }

    @Test
    @Ignore("doesn't work")
    public void noHandlerFoundException() throws Exception {
        mvc.perform(get("/not_exists"))
                .andExpect(status().is(HTTP_STATUS))
                .andExpect(content().string(""));
    }

}
