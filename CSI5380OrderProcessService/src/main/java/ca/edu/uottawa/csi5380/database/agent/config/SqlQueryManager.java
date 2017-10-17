package ca.edu.uottawa.csi5380.database.agent.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:sql-queries.properties")
public class SqlQueryManager {

    private final Environment env;

    @Autowired
    public SqlQueryManager(Environment env) {
        this.env = env;
    }

    /**
     * Get the SQL query string provided in 'sql-queries.properties' using
     * the given query ID.
     *
     * @param queryId - ID of key in the sql-queries property file.
     * @return - The SQL string corresponding to the given ID key value.
     */
    @Cacheable("sqlQueries")
    public String getSqlQuery(String queryId) {
        return env.getProperty(queryId);
    }

}
