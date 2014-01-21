package ru.yaal.examples.database.hibernate.bidirectassosiation.payment.bi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "slip_table")
public class Slip {
    @Id
    @GeneratedValue
    @Column(name = "slip_id_col")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction")
    private Transaction transaction;

    @Column(name = "slip_content_col")
    private String content;

    public Slip() {
    }

    public Slip(Transaction transaction, String content) {
        this.transaction = transaction;
        this.content = content;
    }

    public Slip(String content) {
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object obj) {
        if (getId() != null && obj instanceof Slip) {
            Slip other = (Slip) obj;
            return getId().equals(other.getId());
        }
        return false;
    }
}
