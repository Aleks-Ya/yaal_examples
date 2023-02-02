package common;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Print text to HttpServletResponse output.
 */
@SuppressWarnings("NullableProblems")
public class TextView implements View {

    private final String text;

    public TextView(String text) {
        this.text = text;
    }

    @Override
    public String getContentType() {
        return MediaType.TEXT_PLAIN_VALUE;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        var writer = response.getWriter();
        writer.write(text);
        writer.flush();
    }
}
