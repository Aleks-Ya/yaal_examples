package transaction.declarative.annotation;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TransactionalConfig.class)
class TransactionalTest {

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private PlatformTransactionManager txManager;

    @Test
    void queryForObject() {
        TransactionDefinition definition = new DefaultTransactionAttribute();
        var txStatus = txManager.getTransaction(definition);
        assertThat(template.queryForObject("SELECT title FROM names WHERE id=2", String.class)).isEqualTo("Mary");
        txManager.commit(txStatus);
    }
}