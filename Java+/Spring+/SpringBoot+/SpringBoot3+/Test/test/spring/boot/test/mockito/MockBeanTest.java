package spring.boot.test.mockito;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = MockBeanTest.PersonService.class)
class MockBeanTest {

    @MockBean
    private PersonService personService;

    @Test
    void mockBean() {
        assertThat(personService.getName()).isNull();
        var mockName = "Mary";
        when(personService.getName()).thenReturn(mockName);
        assertThat(personService.getName()).isEqualTo(mockName);
    }

    @Service
    static class PersonService {
        public String getName() {
            return "John";
        }
    }

}