package entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class Shift {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
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

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    @Override
    public String toString() {
        return "entity.Shift{" +
                "id=" + id +
                ", cashier=" + cashier +
                ", createDate=" + createDate +
                ", openDate=" + openDate +
                ", closeDate=" + closeDate +
                '}';
    }
}
