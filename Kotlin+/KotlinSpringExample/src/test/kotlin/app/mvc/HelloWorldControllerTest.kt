package app.mvc

import app.BaseMvcTest
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class HelloWorldControllerTest : BaseMvcTest() {

    @Test
    fun requestParam() {
        mvc.perform(get(HelloWorldController.ENDPOINT).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().string(HelloWorldController.CONTENT))
    }
}
