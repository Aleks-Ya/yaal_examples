package ru.yaal.examples.database.hibernate.bidirectassosiation;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
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
}
