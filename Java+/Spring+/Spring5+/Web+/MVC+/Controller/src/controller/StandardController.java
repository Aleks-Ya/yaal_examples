package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class StandardController {

    @RequestMapping("/requestParam")
    public void missingServletRequestParameterException(
            @RequestParam("abc") String param
    ) {
    }


    @ResponseBody
    @RequestMapping("/model")
    public String helloWorld(Model model) {
        model.addAttribute("message", "Hello World!");
        return "helloWorld";
    }

}
