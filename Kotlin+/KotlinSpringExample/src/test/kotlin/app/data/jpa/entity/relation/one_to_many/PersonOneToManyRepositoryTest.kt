package app.data.jpa.entity.relation.one_to_many

import app.BaseJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class PersonOneToManyRepositoryTest : BaseJpaTest() {
    @Autowired
    private lateinit var personRepository: PersonOneToManyRepository

    @Test
    fun save() {
        val personName = "John"
        val cityName = "SPb"
        val city = CityOneToMany(name = cityName)
        val originPerson = PersonOneToMany(name = personName, city = city)
        val savedPerson = personRepository.save(originPerson)
        entityManager.flush()
        assertThat(savedPerson.id).isNotNull()
        assertThat(savedPerson.name).isEqualTo(personName)
        assertThat(savedPerson.city?.id).isNotNull()
        assertThat(savedPerson.city?.name).isEqualTo(cityName)
        assertThat(personRepository.findById(savedPerson.id!!)).hasValue(savedPerson)
    }
}