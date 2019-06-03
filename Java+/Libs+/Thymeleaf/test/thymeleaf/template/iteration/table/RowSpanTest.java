package thymeleaf.template.iteration.table;

import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import util.ResourceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Merge cells (rows).
 */
public class RowSpanTest {

    @SuppressWarnings("unused")
    private static class Person {
        private String city;
        private String name;

        private Person(String city, String name) {
            this.city = city;
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public String getName() {
            return name;
        }
    }

    private static class Row {
        String city;
        Integer cityRowSpan;
        String name;

        public Row(String city, Integer cityRowSpan, String name) {
            this.city = city;
            this.cityRowSpan = cityRowSpan;
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public Integer getCityRowSpan() {
            return cityRowSpan;
        }

        public String getName() {
            return name;
        }
    }

    @Test
    public void test() {
        ITemplateResolver resolver = new ClassLoaderTemplateResolver();

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        List<Person> personList = Arrays.asList(
                new Person("Moscow", "Petr"),
                new Person("Moscow", "Ann"),
                new Person("SPb", "Simon"),
                new Person("SPb", "John"),
                new Person("SPb", "Mary"),
                new Person("Sochi", "Bob")
        );

        List<Row> rows = new ArrayList<>();

        personList.stream()
                .collect(Collectors.groupingBy(Person::getCity))
                .forEach((city, persons) -> {
                    if (persons.size() > 0) {
                        rows.add(new Row(city, persons.size(), persons.get(0).getName()));
                    }
                    if (persons.size() > 1) {
                        for (int i = 1; i < persons.size(); i++) {
                            rows.add(new Row(null, null, persons.get(i).getName()));
                        }
                    }
                });

        Context context = new Context();
        context.setVariable("rows", rows);

        String template = "thymeleaf/template/iteration/table/row_span_template.html";
        String result = engine.process(template, context);

        String expContent = ResourceUtil.resourceToString(RowSpanTest.class, "row_span_expected.html");
        assertEquals(expContent, result);
    }

}
