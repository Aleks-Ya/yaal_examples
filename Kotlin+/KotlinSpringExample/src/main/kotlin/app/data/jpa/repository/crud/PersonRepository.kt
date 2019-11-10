package app.data.jpa.repository.crud

import org.springframework.data.repository.CrudRepository

interface PersonRepository : CrudRepository<Person?, Long?>