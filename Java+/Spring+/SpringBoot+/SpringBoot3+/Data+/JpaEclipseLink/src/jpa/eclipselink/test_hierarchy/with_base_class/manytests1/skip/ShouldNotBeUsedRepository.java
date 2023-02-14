package jpa.eclipselink.test_hierarchy.with_base_class.manytests1.skip;

import org.springframework.data.repository.CrudRepository;

interface ShouldNotBeUsedRepository extends CrudRepository<ShouldNotBeUsedEntity, Long> {
}
