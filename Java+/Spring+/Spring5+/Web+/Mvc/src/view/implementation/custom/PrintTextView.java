package view.implementation.custom;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static view.implementation.custom.PrintTextView.VIEW_NAME;

/**
 * Take "text" attribute from the model and print it to output.
 */
@Component(VIEW_NAME)
class PrintTextView implements View {
    static final String VIEW_NAME = "print_text";
    static final String MODEL_ATTR_NAME = "text";

    @Override
    public String getContentType() {
        return MediaType.TEXT_PLAIN_VALUE;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        var value = model != null ? model.get(MODEL_ATTR_NAME) : null;
        var text = value != null ? value.toString() : "text is null";
        var writer = response.getWriter();
        writer.write(text);
        writer.flush();
    }
}
