package thymeleaf;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

class HelloWorld {
    @Test
    @Disabled("not work")
    void test() {
        ITemplateResolver resolver = new StringTemplateResolver();

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);
//        IEngineContextFactory contextFactory = new StandardEngineContextFactory();
//        engine.setEngineContextFactory(contextFactory);
        String template = "Hello: #vars";
        Context context = new Context();
        context.setVariable("welcome", "Hello!!!");
        String result = engine.process(template, context);
        System.out.println(result);

    }
}
