package app.data.jpa

import app.BaseJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class PersonRepositoryTest : BaseJpaTest() {
    @Autowired
    private lateinit var personRepository: PersonRepository

    @Test
    fun findAll() {
        val person1 = personRepository.save(Person())
        val person2 = personRepository.save(Person())
        val allPersons = personRepository.findAll()
        assertThat(allPersons).containsOnly(person1, person2)
    }

    @Test
    fun save() {
        val name = "John"
        val origin = Person(name = name)
        val exp = personRepository.save(origin)
        assertThat(exp.id).isNotNull()
        assertThat(exp.name).isEqualTo(name)
        assertThat(personRepository.findById(exp.id!!)).hasValue(exp)
    }
}