package application.exception.handler;

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

import static application.exception.handler.ResponseStatusController.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = ResponseStatusController.class)
public class ResponseStatusControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

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
