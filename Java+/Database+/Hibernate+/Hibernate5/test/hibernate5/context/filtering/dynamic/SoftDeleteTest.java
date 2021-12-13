package hibernate5.context.filtering.dynamic;

import hibernate5.context.session.HibernateSessionFactory5;
import org.junit.jupiter.api.Test;

import static hibernate5.context.filtering.dynamic.AccountEntity.DELETED_ACCOUNT_FILTER;
import static hibernate5.context.filtering.dynamic.AccountEntity.DELETED_STATUS_PARAM;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use {@link org.hibernate.annotations.Filter} annotation for dynamic filtering.
 */
class SoftDeleteTest {

    @Test
    void filter() {
        var john = "John";
        var kate = "Kate";
        var mike = "Mike";
        var peter = "Peter";

        try (var factory436 = HibernateSessionFactory5.makeFactory(AccountEntity.class)) {
            var session = factory436.openSession();
            session.beginTransaction();
            session.save(new AccountEntity(john, valueOf(5000), false));
            session.save(new AccountEntity(kate, valueOf(4000), false));
            session.save(new AccountEntity(mike, valueOf(3000), true));
            session.save(new AccountEntity(peter, valueOf(2000), true));
            session.flush();
            session.getTransaction().commit();

            {
                session.enableFilter(DELETED_ACCOUNT_FILTER).setParameter(DELETED_STATUS_PARAM, false);
                var actAccounts = session
                        .createQuery("from AccountEntity", AccountEntity.class)
                        .getResultList();
                session.disableFilter(DELETED_ACCOUNT_FILTER);
                assertThat(actAccounts).extracting("owner").containsOnly(john, kate);
            }
            {
                session.enableFilter(DELETED_ACCOUNT_FILTER).setParameter(DELETED_STATUS_PARAM, true);
                var actAccounts = session
                        .createQuery("from AccountEntity", AccountEntity.class)
                        .getResultList();
                session.disableFilter(DELETED_ACCOUNT_FILTER);
                assertThat(actAccounts).extracting("owner").containsOnly(mike, peter);
            }
            {
                var actAccounts = session
                        .createQuery("from AccountEntity", AccountEntity.class)
                        .getResultList();
                assertThat(actAccounts).extracting("owner")
                        .containsOnly(john, kate, mike, peter);
            }

        }
    }
}