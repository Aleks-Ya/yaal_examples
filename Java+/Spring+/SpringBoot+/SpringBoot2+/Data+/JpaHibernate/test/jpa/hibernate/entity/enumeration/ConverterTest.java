package jpa.hibernate.entity.enumeration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {Person5Entity.class, Person5Repository.class})
class ConverterTest {
    @Autowired
    private Person5Repository repo;

    @Test
    void converter() {
        var id = 1;
        var person = new Person5Entity(id, "John", Occupation5.DEVELOPER);
        repo.save(person);
        assertThat(repo.findById(id)).contains(new Person5Entity(id, "John", Occupation5.DEVELOPER));

        //Converter is not executed, because it should be registered by org.hibernate.cfg.Configuration#addAttributeConverter()
        assertThat(Occupation5.Occupation5Converter.wasConvertToDatabaseColumnExecuted).isFalse();
        assertThat(Occupation5.Occupation5Converter.wasConvertToEntityAttributeExecuted).isFalse();
    }
}