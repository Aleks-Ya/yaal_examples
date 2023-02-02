package model.attribute;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static model.attribute.WeekdayView.VIEW_NAME;


/**
 * Take "text" attribute from the model and print it to output.
 */
@Component(VIEW_NAME)
@SuppressWarnings("NullableProblems")
class WeekdayView implements View {
    static final String VIEW_NAME = "print_text";
    static final String YEAR_MODEL_ATTR = "year";
    static final String WEEKDAY_MODEL_ATTR = "weekday";
    static final String DAY_MODEL_ATTR = "day";

    @Override
    public String getContentType() {
        return MediaType.TEXT_PLAIN_VALUE;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //noinspection ConstantConditions
        if (model != null) {
            var weekdayObject = model.get(WEEKDAY_MODEL_ATTR);
            var dayObject = model.get(DAY_MODEL_ATTR);
            var yearObject = model.get(YEAR_MODEL_ATTR);
            var weekday = weekdayObject != null ? weekdayObject.toString() : "no data";
            var day = dayObject != null ? dayObject.toString() : "no data";
            var year = yearObject != null ? yearObject.toString() : "no data";

            var writer = response.getWriter();
            writer.write(day + " " + weekday + " " + year);
            writer.flush();
        }
    }
}
