package ca.edu.uottawa.csi5380.database.agent;

import ca.edu.uottawa.csi5380.database.agent.config.SqlQueryManager;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class DataAgent {

    private final JdbcTemplate jdbcTemplate;
    private final SqlQueryManager sqlQueryManager;

    @Autowired
    public DataAgent(ComboPooledDataSource dataSource, SqlQueryManager sqlQueryManager) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.sqlQueryManager = sqlQueryManager;
    }

    /**
     * Executes the SQL query specified by the query ID, with the given parameter list,
     * and returns the number of rows affected.
     *
     * @param queryId - ID of the SQL query found using the SqlQueryManager.
     * @param params  - List of query parameters to inject into the SQL statement found
     *                by the SqlQueryManager.
     * @return - The number of rows affected.
     */
    public int executeSQL(String queryId, Object[] params) {
        return jdbcTemplate.update(sqlQueryManager.getSqlQuery(queryId), params);
    }

    /**
     * Executes the SQL query specified by the query ID, with the given parameter list,
     * and returns disconnected ResultSet data (all resources safely closed) using
     * Spring JDBC.
     *
     * @param queryId - ID of the SQL query found using the SqlQueryManager.
     * @param params  - List of query parameters to inject into the SQL statement found
     *                by the SqlQueryManager.
     * @return - Disconnected ResultSet Data.
     */
    public SqlRowSet getQueryResult(String queryId, Object[] params) {
        return jdbcTemplate.queryForRowSet(sqlQueryManager.getSqlQuery(queryId), params);
    }

}
