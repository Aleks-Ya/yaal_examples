package view.implementation.custom;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

import static view.implementation.custom.PrintTextView.MODEL_ATTR_NAME;
import static view.implementation.custom.PrintTextView.VIEW_NAME;

@Controller
class CustomViewController {
    static final String ENDPOINT = "/show";

    @RequestMapping(value = ENDPOINT)
    public ModelAndView show(@RequestParam String message) {
        var model = new HashMap<String, Object>();
        model.put(MODEL_ATTR_NAME, message);
        return new ModelAndView(VIEW_NAME, model);
    }
}
