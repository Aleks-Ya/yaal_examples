package mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static mockito.MockTest.CityService.TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {MockTest.PersonService.class, MockTest.CityService.class})
class MockTest {

    @Mock
    private PersonService personService;

    @Autowired
    private CityService cityService;

    @Test
    void mock() {
        var mockName = "Mary";
        when(personService.getName()).thenReturn(mockName);
        assertThat(personService.getName()).isEqualTo(mockName);
        assertThat(cityService.getTitle()).isEqualTo(TITLE);
    }

    @Service
    static class PersonService {
        public String getName() {
            return "John";
        }
    }

    @Service
    static class CityService {
        public static final String TITLE = "London";

        public String getTitle() {
            return TITLE;
        }
    }

}