package ru.yaal.merch.bookshelf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Configuration
@ComponentScan(basePackages = "ru.yaal.merch.bookshelf.repository.impl")
class RepoConfig {

    @Bean
    @Profile(Profiles.PROD)
    public DataSource prod(
            @Value("${jdbc.url}") String url,
            @Value("${jdbc.username}") String user,
            @Value("${jdbc.password}") String pass
    ) {
        return new DriverManagerDataSource(url, user, pass);
    }

    @Bean
    @Profile(Profiles.DEMO)
    public DataSource demo() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(H2)
                .setScriptEncoding("UTF-8")
                .addScripts("sql/init-h2.sql", "sql/insert_demo_data.sql")
                .build();
    }

    @Bean
    public NamedParameterJdbcTemplate namedTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean(name = "books")
    public SimpleJdbcInsert books(DataSource dataSource) {
        return new SimpleJdbcInsert(dataSource)
                .withTableName("books")
                .usingGeneratedKeyColumns("id");
    }

    @Bean(name = "authors")
    public SimpleJdbcInsert authors(DataSource dataSource) {
        return new SimpleJdbcInsert(dataSource)
                .withTableName("authors")
                .usingGeneratedKeyColumns("id");
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
