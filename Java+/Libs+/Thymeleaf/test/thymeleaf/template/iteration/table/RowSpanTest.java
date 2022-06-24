package thymeleaf.template.iteration.table;

import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import util.ResourceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Merge cells (rows).
 */
class RowSpanTest {
    @Test
    void test() {
        ITemplateResolver resolver = new ClassLoaderTemplateResolver();

        var engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        var personList = Arrays.asList(
                new Person("Moscow", "Petr"),
                new Person("Moscow", "Ann"),
                new Person("SPb", "Simon"),
                new Person("SPb", "John"),
                new Person("SPb", "Mary"),
                new Person("Sochi", "Bob")
        );

        List<Row> rows = new ArrayList<>();

        personList.stream()
                .collect(Collectors.groupingBy(Person::getCity, LinkedHashMap::new, Collectors.toList()))
                .forEach((city, persons) -> {
                    if (persons.size() > 0) {
                        rows.add(new Row(city, persons.size(), persons.get(0).getName()));
                    }
                    if (persons.size() > 1) {
                        for (var i = 1; i < persons.size(); i++) {
                            rows.add(new Row(null, null, persons.get(i).getName()));
                        }
                    }
                });

        var context = new Context();
        context.setVariable("rows", rows);

        var template = "thymeleaf/template/iteration/table/row_span_template.html";
        var result = engine.process(template, context);

        var expContent = ResourceUtil.resourceToString(RowSpanTest.class, "row_span_expected.html");
        assertThat(result).isEqualTo(expContent);
    }

    @SuppressWarnings("unused")
    private static class Person {
        private String city;
        private String name;

        private Person(String city, String name) {
            this.city = city;
            this.name = name;
        }

        String getCity() {
            return city;
        }

        String getName() {
            return name;
        }
    }

    @SuppressWarnings("unused")
    private static class Row {
        String city;
        Integer cityRowSpan;
        String name;

        Row(String city, Integer cityRowSpan, String name) {
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

}
