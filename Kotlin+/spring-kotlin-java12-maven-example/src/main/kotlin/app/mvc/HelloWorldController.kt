package app.mvc

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class HelloWorldController {
    companion object {
        const val ENDPOINT = "/hello"
        const val CONTENT = "Hello, World!"
    }

    @ResponseBody
    @GetMapping(path = [ENDPOINT])
    fun greeting(): String {
        return CONTENT
    }
}