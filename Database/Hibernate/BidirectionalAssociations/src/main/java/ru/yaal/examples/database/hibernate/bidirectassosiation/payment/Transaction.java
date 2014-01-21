package ru.yaal.examples.database.hibernate.bidirectassosiation.payment;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "transaction_table")
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private Payment payment;
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
