package transaction.programmatic.template;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TxTemplateConfig.class)
class TxTemplateTest {

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private TransactionTemplate txTemplate;

    @Test
    void queryForObject() {
        TransactionDefinition definition = new DefaultTransactionAttribute();
        var txStatus = txManager.getTransaction(definition);

        var result = txTemplate.execute(
                status -> template.queryForObject("SELECT title FROM names WHERE id=2", String.class));
        txManager.commit(txStatus);
        assertThat(result).isEqualTo("Mary");
    }
}