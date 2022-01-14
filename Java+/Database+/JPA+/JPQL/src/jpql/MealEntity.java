package jpql;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "meal")
public class MealEntity {
    @Id
//    @SequenceGenerator(name = "meal_generator", sequenceName = "meal_id_seq", allocationSize = 1)
//    @GeneratedValue(generator = "meal_generator")
    private Long id;
    private String name;

    @ManyToOne
    private UserEntity user;

    public MealEntity() {
    }

    public MealEntity(Long id, String name, UserEntity user) {
        this.id = id;
        this.name = name;
        this.user = user;
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealEntity that = (MealEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, user);
    }
}
