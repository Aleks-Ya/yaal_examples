package ru.yaal.examples.database.hibernate.bidirectassosiation;

import javax.persistence.*;

@Entity
@Embeddable
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    @EmbeddedId
    private Payment payment;

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
