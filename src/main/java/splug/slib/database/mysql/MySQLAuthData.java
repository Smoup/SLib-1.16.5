package splug.slib.database.mysql;

import lombok.*;


@Builder
public record MySQLAuthData(
        String host,
        String port,
        String username,
        String password,
        String database,
        int poolSize
) {
}
