package method_execution_time.builtin.static_logger;

import org.springframework.stereotype.Service;

@Service
class PersonService {
    String getFullName(Person person) throws InterruptedException {
        Thread.sleep(500);
        return person.getLastName() + " " + person.getFirstName();
    }
}
