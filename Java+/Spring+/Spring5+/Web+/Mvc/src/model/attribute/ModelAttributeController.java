package model.attribute;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static model.attribute.WeekdayView.DAY_MODEL_ATTR;

@Controller
class ModelAttributeController {
    static final String ENDPOINT = "/week";

    @RequestMapping(value = ENDPOINT)
    public ModelAndView show(@ModelAttribute("model") ModelMap model) {
        model.put(DAY_MODEL_ATTR, "31");
        return new ModelAndView(WeekdayView.VIEW_NAME, model);
    }

    @SuppressWarnings("unused")
    @ModelAttribute(WeekdayView.WEEKDAY_MODEL_ATTR)
    String dayOfWeek() {
        return "Monday";
    }

    @SuppressWarnings("unused")
    @ModelAttribute(WeekdayView.YEAR_MODEL_ATTR)
    Integer year(@RequestParam Integer yearParam) {
        return yearParam;
    }

}
