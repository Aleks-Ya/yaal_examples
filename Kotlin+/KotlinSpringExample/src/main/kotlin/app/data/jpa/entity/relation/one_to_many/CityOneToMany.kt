package app.data.jpa.entity.relation.one_to_many

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class CityOneToMany(
        @Id
        @GeneratedValue
        val id: Long? = null,
        val name: String? = null
)