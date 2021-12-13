package hibernate5.mapping.assosiation.unidirectional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class People {
    @Id
    @GeneratedValue
    private Long id;
}