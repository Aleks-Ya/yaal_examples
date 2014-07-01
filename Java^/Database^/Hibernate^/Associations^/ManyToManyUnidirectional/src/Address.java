import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    private Set<People> peoples;

    public Set<People> getPeoples() {
        return peoples;
    }

    public void setPeoples(Set<People> peoples) {
        this.peoples = peoples;
    }
}