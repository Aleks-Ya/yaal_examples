package filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Добавляет атрибут (коллекцию) к запросу.
 */
@WebFilter(urlPatterns = "/jstl/for_each.jsp")
public class PutCollectionAttributeToRequestFilter implements Filter {
    public static final String ATTR_NAME = "collectionAttr";
    public static final ArrayList<String> ATTR_VALUE = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ATTR_VALUE.add("Moscow");
        ATTR_VALUE.add("London");
        ATTR_VALUE.add("New-York");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute(ATTR_NAME, ATTR_VALUE);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}