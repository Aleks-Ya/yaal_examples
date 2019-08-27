package method_execution_time.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Processing invocation of the same class method.  
 */
@Service
class PersonServiceSelfMethod {
    @Autowired
    private PersonServiceSelfMethod self;

    String getFullName(Person person) throws InterruptedException {
        Thread.sleep(300);
        return self.privateMethod(person);
    }

    /**
     * Can't be private.
     */
    @SuppressWarnings("WeakerAccess")
    String privateMethod(Person person) throws InterruptedException {
        Thread.sleep(200);
        return person.getLastName() + " " + person.getFirstName();
    }
}
