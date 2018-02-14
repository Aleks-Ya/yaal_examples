package common;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@RunWith(SpringRunner.class)
public abstract class BaseTest {
    @Autowired
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    private WebApplicationContext context;

    protected MockMvc mvc;

    @Before
    public void setupMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}