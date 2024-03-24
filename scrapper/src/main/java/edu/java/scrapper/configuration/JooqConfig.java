package edu.java.scrapper.configuration;

import javax.sql.DataSource;
import lombok.Setter;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameCase;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

@Configuration
@Setter
public class JooqConfig {
    @Bean
    @Autowired
    public DSLContext dsl(@Qualifier("jdbcSource") DataSource dataSource) {
        DataSourceConnectionProvider connectionProvider =
            new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.set(connectionProvider);
        configuration.set(SQLDialect.POSTGRES);
        configuration.settings().setRenderNameCase(RenderNameCase.LOWER);
        return new DefaultDSLContext(configuration);
    }

}
