package jpa.eclipselink.test_hierarchy.with_base_class.manytests1;

import jpa.eclipselink.JpaConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@DirtiesContext
@ContextConfiguration(classes = JpaConfig.class)
public abstract class BaseEclipseLink1Test {
}
