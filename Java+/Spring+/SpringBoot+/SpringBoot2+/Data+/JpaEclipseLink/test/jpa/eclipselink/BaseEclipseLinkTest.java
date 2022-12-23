package jpa.eclipselink;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@DirtiesContext
@ContextConfiguration(classes = JpaConfig.class)
public abstract class BaseEclipseLinkTest {
}
