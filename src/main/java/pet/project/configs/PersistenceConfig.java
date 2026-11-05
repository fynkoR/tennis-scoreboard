package pet.project.configs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.hibernate.HibernateTransactionManager;
import org.springframework.orm.jpa.hibernate.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:hibernate.properties")
public class PersistenceConfig {
    private final Environment environment;

    @Autowired
    public PersistenceConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource(){
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(environment.getRequiredProperty("db.driver_class"));
        config.setJdbcUrl(environment.getRequiredProperty("db.url"));
        config.setUsername(environment.getRequiredProperty("db.username"));
        config.setPassword(environment.getRequiredProperty("db.password"));

        return new HikariDataSource(config);
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("pet.project.entities");

        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(LocalSessionFactoryBean sessionFactory) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory.getObject());

        return hibernateTransactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
