package bean.autowire;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Autowire not required bean via constructor.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AutowireAbsentBeanViaConstructor.CarService.class})
class AutowireAbsentBeanViaConstructor {
    private final PersonService personService;
    private final CarService carService;

    public AutowireAbsentBeanViaConstructor(
            @Autowired(required = false) PersonService personService,
            @Autowired CarService carService) {
        this.personService = personService;
        this.carService = carService;
    }

    @Test
    void autowire() {
        assertThat(personService).isNull();
        assertThat(carService).isNotNull();
    }

    @Service
    static class PersonService {
    }

    @Service
    static class CarService {
    }
}
