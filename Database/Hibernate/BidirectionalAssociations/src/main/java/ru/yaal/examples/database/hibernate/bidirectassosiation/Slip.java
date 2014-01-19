package ru.yaal.examples.database.hibernate.bidirectassosiation;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Slip {
    @Id
    @GeneratedValue
    private Long id;
    @EmbeddedId
    private Transaction transaction;
    private String content;

    public Slip() {
    }

    public Slip(Transaction transaction, String content) {
        this.transaction = transaction;
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
}
