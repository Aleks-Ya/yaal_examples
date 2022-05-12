package selenium.tax;

import java.math.BigDecimal;
import java.time.LocalDate;

record TaxSource(
        BigDecimal id,
        String emitter,
        BigDecimal sumTotal,
        LocalDate payDate,
        BigDecimal sumPaid,
        BigDecimal currencyRate,
        String ISIN) {
}
