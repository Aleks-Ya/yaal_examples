package thymeleaf.template.iteration.table;

import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import util.ResourceUtil;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IterateMapTest {

    @SuppressWarnings("unused")
    private static class Product {
        private Double price;
        private Boolean inStock;

        private Product(Double price, Boolean inStock) {
            this.price = price;
            this.inStock = inStock;
        }

        public Double getPrice() {
            return price;
        }

        public Boolean getInStock() {
            return inStock;
        }
    }

    @Test
    void test() {
        ITemplateResolver resolver = new ClassLoaderTemplateResolver();

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Map<String, Product> nameToProductMap = new HashMap<>();
        nameToProductMap.put("Bread", new Product(35.4, true));
        nameToProductMap.put("Milk", new Product(70.9, true));
        nameToProductMap.put("Gun", new Product(10_370.5, false));

        Context context = new Context();
        context.setVariable("nameToProductMap", nameToProductMap);

        String template = "thymeleaf/template/iteration/table/iterate_map_template.html";
        String result = engine.process(template, context);

        String expContent = ResourceUtil.resourceToString(IterateMapTest.class, "iterate_map_expected.html");
        assertEquals(expContent, result);
    }

}
