package thymeleaf.template.iteration.table;

import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import util.ResourceUtil;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IterateListTest {

    @SuppressWarnings("unused")
    private static class Product {
        private String name;
        private Double price;
        private Boolean inStock;

        private Product(String name, Double price, Boolean inStock) {
            this.name = name;
            this.price = price;
            this.inStock = inStock;
        }

        public String getName() {
            return name;
        }

        public Double getPrice() {
            return price;
        }

        public Boolean getInStock() {
            return inStock;
        }
    }

    @Test
    public void test() {
        ITemplateResolver resolver = new ClassLoaderTemplateResolver();

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        List<Product> prods = Arrays.asList(
                new Product("Bread", 35.4, true),
                new Product("Milk", 70.9, true),
                new Product("Gun", 10_370.5, false)
        );
        Context context = new Context();
        context.setVariable("prods", prods);

        String template = "thymeleaf/template/iteration/table/iterate_list_template.html";
        String result = engine.process(template, context);

        String expContent = ResourceUtil.resourceToString(IterateListTest.class, "iterate_list_expected.html");
        assertEquals(expContent, result);
    }

}
