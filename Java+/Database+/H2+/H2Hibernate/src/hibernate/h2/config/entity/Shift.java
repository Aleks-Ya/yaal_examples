package hibernate.h2.config.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private Cashier cashier;
    private Date createDate;
    private Date openDate;
    private Date closeDate;

    public Shift() {
    }

    public Shift(Cashier cashier, Date createDate, Date openDate, Date closeDate) {
        this.cashier = cashier;
        this.createDate = createDate;
        this.openDate = openDate;
        this.closeDate = closeDate;
    }

    @Override
    public String toString() {
        return String.format("Shift[id=%d cashier=%s createDate=%s openDate=%s closeDate=%s]",
                id, cashier, createDate, openDate, closeDate);
    }
}