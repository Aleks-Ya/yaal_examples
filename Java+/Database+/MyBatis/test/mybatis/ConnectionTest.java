package mybatis;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

/**
 * @author Aleksey Yablokov
 */
class ConnectionTest {
    @Test
    void name() {
        var dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:");

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        var environment = new Environment("development", transactionFactory, dataSource);
        var configuration = new Configuration(environment);
//        configuration.addMapper(BlogMapper.class);
        var sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        var sqlSession = sqlSessionFactory.openSession();
//        sqlSession.update("create table aaa(in int)");

    }
}
