package app.data.jpa.entity.constraint.unique

import javax.persistence.*

@Entity
@Table(uniqueConstraints= [UniqueConstraint(columnNames=arrayOf("firstName", "secondName"))])
data class PersonUnique(
        @Id
        @GeneratedValue
        val id: Long? = null,
        val firstName: String? = null,
        val secondName: String? = null
)