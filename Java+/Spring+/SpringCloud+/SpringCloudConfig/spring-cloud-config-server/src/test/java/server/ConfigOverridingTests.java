package server;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConfigOverridingTests extends BaseMvcTest {

    @Test
    public void notExistApplicationNotExistProfile() throws Exception {
        String expContent = ResourceUtil.resourceToString(ConfigOverridingTests.class, "notExistApp_notExistProfile_body.json");
        mvc.perform(get("/notExistApp/notExistProfile"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expContent));
    }
    
    @Test
    public void applicationNotExistProfile() throws Exception {
        String expContent = ResourceUtil.resourceToString(ConfigOverridingTests.class, "app1_notExistProfile_body.json");
        mvc.perform(get("/app1/notExistProfile"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expContent));
    }

    @Test
    public void applicationProfile() throws Exception {
        String expContent = ResourceUtil.resourceToString(ConfigOverridingTests.class, "app1_profileA_body.json");
        mvc.perform(get("/app1/profileA"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expContent));
    }

    @Test
    public void applicationProfileLabel() throws Exception {
        String expContent = ResourceUtil.resourceToString(ConfigOverridingTests.class, "app1_profileA_labelA_body.json");
        mvc.perform(get("/app1/profileA/labelA"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expContent));
    }

}
