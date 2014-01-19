package ru.yaal.examples.database.hibernate.bidirectassosiation;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Payment payment;
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

}
