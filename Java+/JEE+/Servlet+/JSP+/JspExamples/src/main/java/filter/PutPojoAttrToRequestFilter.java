package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Добавляет атрибут к запросу.
 */
@WebFilter(urlPatterns = "/el/read_request_attribute.jsp")
public class PutPojoAttrToRequestFilter implements Filter {
    public static final String ATTR_NAME = "pojoAttr";
    public static final Object ATTR_VALUE = new Cat("Barsic", 2, false);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute(ATTR_NAME, ATTR_VALUE);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    @SuppressWarnings("unused")
    public static class Cat {
        private final String name;
        private final Integer age;
        private final Boolean homeless;

        Cat(String name, Integer age, Boolean homeless) {
            this.name = name;
            this.age = age;
            this.homeless = homeless;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        //isHomeless isn't supported
        public Boolean getHomeless() {
            return homeless;
        }
    }
}