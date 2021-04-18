package common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public abstract class BaseTest {
    @Autowired
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    private WebApplicationContext context;

    protected MockMvc mvc;

    @BeforeEach
    public void setupMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}