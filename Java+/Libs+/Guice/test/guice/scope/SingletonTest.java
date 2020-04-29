package guice.scope;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.junit.Test;

import javax.inject.Singleton;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SingletonTest {

    @Singleton
    static class CurrencyFormatter {
        public String format(double value) {
            return String.format("$%.2f", value);
        }
    }

    static class Account {
        private final CurrencyFormatter currencyFormatter;
        private double balance;

        @Inject
        Account(CurrencyFormatter currencyFormatter) {
            this.currencyFormatter = currencyFormatter;
        }

        public String getBalance() {
            return currencyFormatter.format(balance);
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public CurrencyFormatter getCurrencyFormatter() {
            return currencyFormatter;
        }
    }

    @Test
    public void bind() {
        Injector injector = Guice.createInjector();

        Account account1 = injector.getInstance(Account.class);
        account1.setBalance(1000);
        String balance1 = account1.getBalance();
        assertThat(balance1, equalTo("$1000.00"));

        Account account2 = injector.getInstance(Account.class);
        account2.setBalance(2000);
        String balance2 = account2.getBalance();
        assertThat(balance2, equalTo("$2000.00"));

        CurrencyFormatter currencyFormatter1 = account1.getCurrencyFormatter();
        CurrencyFormatter currencyFormatter2 = account2.getCurrencyFormatter();
        assertThat(currencyFormatter1, is(currencyFormatter2));
    }
}