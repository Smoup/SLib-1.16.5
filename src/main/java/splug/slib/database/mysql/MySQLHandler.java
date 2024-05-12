package splug.slib.database.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter @SuppressWarnings("unused")
@EqualsAndHashCode @ToString
public class MySQLHandler {
    private final HikariDataSource dataSource;

    public MySQLHandler(MySQLAuthData authData, int maxPoolSize) {
        final HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://%s:%s/%s".formatted(
                authData.host(), authData.port(), authData.database()
        ));
        config.setUsername(authData.username());
        config.setPassword(authData.password());
        config.setMaximumPoolSize(maxPoolSize);

        dataSource = new HikariDataSource(config);
    }

    public void unload() {
        dataSource.close();
    }
}
