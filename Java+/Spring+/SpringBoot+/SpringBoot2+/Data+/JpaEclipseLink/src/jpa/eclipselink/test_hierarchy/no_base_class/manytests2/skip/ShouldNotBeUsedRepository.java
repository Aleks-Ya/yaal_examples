package jpa.eclipselink.test_hierarchy.no_base_class.manytests2.skip;

import org.springframework.data.repository.CrudRepository;

interface ShouldNotBeUsedRepository extends CrudRepository<ShouldNotBeUsedEntity, Long> {
}
