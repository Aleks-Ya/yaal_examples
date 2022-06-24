package transaction.programmatic.template;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.support.TransactionTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TxTemplateConfig.class)
public class TxTemplateTest {

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private TransactionTemplate txTemplate;

    @Test
    void queryForObject() {
        TransactionDefinition definition = new DefaultTransactionAttribute();
        TransactionStatus txStatus = txManager.getTransaction(definition);

        String result = txTemplate.execute(
                status -> template.queryForObject("SELECT title FROM names WHERE id=2", String.class));
        txManager.commit(txStatus);
        assertEquals("Mary", result);
    }
}