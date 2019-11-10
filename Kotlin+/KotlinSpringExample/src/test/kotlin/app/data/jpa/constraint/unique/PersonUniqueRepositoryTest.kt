package app.data.jpa.constraint.unique

import app.BaseJpaTest
import app.data.jpa.entity.constraint.unique.PersonUnique
import app.data.jpa.entity.constraint.unique.PersonUniqueRepository
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.exception.ConstraintViolationException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.PersistenceException
import kotlin.test.assertFailsWith

internal class PersonUniqueRepositoryTest : BaseJpaTest() {
    @Autowired
    private lateinit var personUniqueRepository: PersonUniqueRepository

    @Test
    fun saveUnique() {
        val firstName = "John"
        val secondName = "Smith"
        val origin = PersonUnique(firstName = firstName, secondName = secondName)
        val exp = personUniqueRepository.save(origin)
        entityManager.flush()
        assertThat(exp.id).isNotNull()
        assertThat(exp.firstName).isEqualTo(firstName)
        assertThat(exp.secondName).isEqualTo(secondName)
        assertThat(personUniqueRepository.findById(exp.id!!)).hasValue(exp)
    }

    @Test
    fun saveNotUnique() {
        val e = assertFailsWith<PersistenceException> {
            val firstName = "John"
            val secondName = "Smith"
            personUniqueRepository.save(PersonUnique(firstName = firstName, secondName = secondName))
            personUniqueRepository.save(PersonUnique(firstName = firstName, secondName = secondName))
            entityManager.flush()
        }
        assertThat(e.cause).isInstanceOf(ConstraintViolationException::class.java)
    }
}