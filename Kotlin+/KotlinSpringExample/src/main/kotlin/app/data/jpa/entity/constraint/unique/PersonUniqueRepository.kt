package app.data.jpa.entity.constraint.unique

import org.springframework.data.repository.CrudRepository

interface PersonUniqueRepository : CrudRepository<PersonUnique?, Long?>