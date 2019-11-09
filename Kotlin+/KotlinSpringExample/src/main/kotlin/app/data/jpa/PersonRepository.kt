package app.data.jpa

import org.springframework.data.repository.CrudRepository

interface PersonRepository : CrudRepository<Person?, Long?>