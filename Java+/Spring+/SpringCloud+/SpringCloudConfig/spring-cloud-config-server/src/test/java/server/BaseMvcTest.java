package server;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
public abstract class BaseMvcTest  extends BaseTest {
    @Autowired
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    private WebApplicationContext context;

    protected MockMvc mvc;

    @Before
    public void setupMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}