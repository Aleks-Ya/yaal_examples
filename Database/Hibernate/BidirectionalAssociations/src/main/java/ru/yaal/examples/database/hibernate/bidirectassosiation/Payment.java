package ru.yaal.examples.database.hibernate.bidirectassosiation;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Embeddable
public class Payment {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
