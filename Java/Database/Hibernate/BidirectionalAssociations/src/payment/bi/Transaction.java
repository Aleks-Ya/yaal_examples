package payment.bi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "transaction_table")
public class Transaction {
    @Id
    @GeneratedValue
    @Column(name = "transaction_id_col")
    private Long id;

    @OneToOne
    private Payment payment;

    @Column(name = "transaction_slips_col")
    @OneToMany(mappedBy = "transaction")
    private Set<Slip> slips;

    public Transaction() {
    }

    public Transaction(Payment payment) {
        this.payment = payment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Set<Slip> getSlips() {
        return slips;
    }

    public void setSlips(Set<Slip> slips) {
        this.slips = slips;
    }

    @Override
    public boolean equals(Object obj) {
        if (getId() != null && obj instanceof Transaction) {
            Transaction other = (Transaction) obj;
            return getId().equals(other.getId());
        }
        return false;
    }
}
