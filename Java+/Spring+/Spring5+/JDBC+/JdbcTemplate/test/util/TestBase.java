package util;

import conf.Config;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Config.class)
public abstract class TestBase {

    @Autowired
    protected JdbcTemplate template;

    @Autowired
    protected NamedParameterJdbcTemplate namedTemplate;

    @Autowired
    private TemplateUtil templateUtil;

    @Before
    public void setUp() {
        templateUtil.recreateNamesTable(true);
    }

}