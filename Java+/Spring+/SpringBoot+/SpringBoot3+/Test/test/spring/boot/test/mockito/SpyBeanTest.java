package spring.boot.test.mockito;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.stereotype.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static spring.boot.test.mockito.SpyBeanTest.PersonService.FAMILY_NAME;
import static spring.boot.test.mockito.SpyBeanTest.PersonService.GIVEN_NAME;

@SpringBootTest(classes = SpyBeanTest.PersonService.class)
class SpyBeanTest {

    @SpyBean
    private PersonService personService;

    @Test
    void spyBean() {
        assertThat(personService.getGivenName()).isEqualTo(GIVEN_NAME);
        assertThat(personService.getFamilyName()).isEqualTo(FAMILY_NAME);
        var mockName = "Mary";
        when(personService.getGivenName()).thenReturn(mockName);
        assertThat(personService.getGivenName()).isEqualTo(mockName);
        assertThat(personService.getFamilyName()).isEqualTo(FAMILY_NAME);
    }

    @Service
    static class PersonService {
        public static final String GIVEN_NAME = "John";
        public static final String FAMILY_NAME = "Smith";

        public String getGivenName() {
            return GIVEN_NAME;
        }

        public String getFamilyName() {
            return FAMILY_NAME;
        }
    }

}