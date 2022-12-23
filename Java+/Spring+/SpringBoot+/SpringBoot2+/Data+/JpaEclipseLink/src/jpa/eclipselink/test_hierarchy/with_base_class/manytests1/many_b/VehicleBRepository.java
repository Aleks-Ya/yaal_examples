package jpa.eclipselink.test_hierarchy.with_base_class.manytests1.many_b;

import org.springframework.data.repository.CrudRepository;

interface VehicleBRepository extends CrudRepository<VehicleB, Long> {
}
