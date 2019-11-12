package app

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.jdbc.core.JdbcTemplate
import javax.persistence.EntityManager
import javax.sql.DataSource

@DataJpaTest
abstract class BaseJpaTest {
    @Autowired
    protected lateinit var dataSource: DataSource
    @Autowired
    protected lateinit var jdbcTemplate: JdbcTemplate
    @Autowired
    protected lateinit var entityManager: EntityManager
}