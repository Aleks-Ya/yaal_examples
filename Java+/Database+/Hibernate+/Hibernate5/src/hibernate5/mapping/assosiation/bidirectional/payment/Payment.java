package hibernate5.mapping.assosiation.bidirectional.payment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payment_table")
public class Payment {
    @Id
    @GeneratedValue
    @Column(name = "payment_id_col")
    private Long id;

    @OneToOne(mappedBy = "payment")
    private Transaction transaction;

    @Column(name = "payment_description_col")
    private String description;

    public Payment() {
    }

    public Payment(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (getId() != null && obj instanceof Payment) {
            Payment other = (Payment) obj;
            return getId().equals(other.getId());
        }
        return false;
    }
}
