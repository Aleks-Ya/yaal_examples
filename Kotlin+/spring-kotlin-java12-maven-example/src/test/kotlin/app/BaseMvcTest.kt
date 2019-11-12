package app

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest
abstract class BaseMvcTest {
    @Autowired
    protected lateinit var mvc: MockMvc
}