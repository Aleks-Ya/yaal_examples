package ru.yaal.examples.database.hibernate.bidirectassosiation;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Embeddable
public class Transaction {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
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
