package jpa.eclipselink.test_hierarchy.with_base_class.manytests1.many_a;

import org.springframework.data.repository.CrudRepository;

interface VehicleARepository extends CrudRepository<VehicleA, Long> {
}
