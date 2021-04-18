package util;

import conf.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
public abstract class TestBase {

    @Autowired
    protected JdbcTemplate template;

    @Autowired
    protected NamedParameterJdbcTemplate namedTemplate;

    @Autowired
    private TemplateUtil templateUtil;

    @BeforeEach
    public void setUp() {
        templateUtil.recreateNamesTable(true);
    }

}