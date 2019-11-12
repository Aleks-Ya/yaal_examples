package app.data.jpa.entity.relation.one_to_many

import javax.persistence.*

@Entity
data class PersonOneToMany(
        @Id
        @GeneratedValue
        val id: Long? = null,
        val name: String? = null,
        @ManyToOne(cascade = [CascadeType.ALL])
        val city: CityOneToMany? = null
)