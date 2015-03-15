package bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Проверяем инициализацию бинов, отмеченный @Service.
 */
@Service
public class AnyService {

    @Autowired
    private AnyRepository repo;

    @PostConstruct
    public void postConstruct() {
        System.out.println("============= SERVICE INSTANTIATED =============");
        System.out.println("==== REPO IS: " + repo);
    }

    public String getServiceMessage() {
        return "REPO: " + repo.getRepositoryMessage() + ", SERVICE: Hi!";
    }
}