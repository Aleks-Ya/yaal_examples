package hibernate.mapping.assosiation.bidirectional.people;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany
    @JoinTable
    private Set<People> peoples;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<People> getPeoples() {
        return peoples;
    }

    public void setPeoples(Set<People> peoples) {
        this.peoples = peoples;
    }
}
