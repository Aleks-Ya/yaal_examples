package jpql;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
//@Table(name = "users")
public class UserEntity {

    @Id
//    @SequenceGenerator(name = "user_generator", sequenceName = "user_id_seq", allocationSize = 1)
//    @GeneratedValue(generator = "user_generator")
    private Long id;

    private String name;

    public UserEntity() {
    }

    public UserEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
