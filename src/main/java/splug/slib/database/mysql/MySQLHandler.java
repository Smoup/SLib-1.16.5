package splug.slib.database.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;

import java.sql.Connection;
import java.sql.SQLException;

@Data @SuppressWarnings("unused")
public class MySQLHandler {
    private final HikariDataSource dataSource;

    public MySQLHandler(MySQLAuthData authData) {
        final HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://%s:%s/%s?useSSL=true&verifyServerCertificate=false".formatted(
                authData.host(), authData.port(), authData.database()
        ));
        config.setUsername(authData.username());
        config.setPassword(authData.password());
        config.setMaximumPoolSize(authData.poolSize());

        dataSource = new HikariDataSource(config);
    }

    public void unload() {
        dataSource.close();
    }

    public Connection getConn() throws SQLException {
        return dataSource.getConnection();
    }
}
