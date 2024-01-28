package guice.scope;

import com.google.inject.Guice;
import com.google.inject.Inject;
import org.junit.jupiter.api.Test;

import jakarta.inject.Singleton;

import static org.assertj.core.api.Assertions.assertThat;

class SingletonTest {

    @Test
    void bind() {
        var injector = Guice.createInjector();

        var account1 = injector.getInstance(Account.class);
        account1.setBalance(1000);
        var balance1 = account1.getBalance();
        assertThat(balance1).isEqualTo("$1000.00");

        var account2 = injector.getInstance(Account.class);
        account2.setBalance(2000);
        var balance2 = account2.getBalance();
        assertThat(balance2).isEqualTo("$2000.00");

        var currencyFormatter1 = account1.getCurrencyFormatter();
        var currencyFormatter2 = account2.getCurrencyFormatter();
        assertThat(currencyFormatter1).isSameAs(currencyFormatter2);
    }

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
}