package app.data.jpa.entity.relation.one_to_many

import org.springframework.data.repository.CrudRepository

interface PersonOneToManyRepository : CrudRepository<PersonOneToMany?, Long?>