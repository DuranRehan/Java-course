package g56055.atlg.stibride.models.data.dao;

import g56055.atlg.stibride.models.utils.config.ConfigManager;
import g56055.atlg.stibride.models.data.exception.RepositoryException;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DBManager {
    private Connection connection;

    private DBManager() {
    }

    Connection getConnection() throws RepositoryException {
        try {
            ConfigManager.getInstance().load();
        } catch (Exception e) {
            System.out.println("impossible de charger le fichier de config " + e.getMessage());
        }
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        String jdbcUrl = "jdbc:sqlite:" + ConfigManager.getInstance().getProperties("db.url");
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(jdbcUrl,config.toProperties());
            } catch (SQLException ex) {
                throw new RepositoryException("Connexion impossible: " + ex.getMessage());
            }
        }
        return connection;
    }

    static DBManager getInstance() {
        return DBManagerHolder.INSTANCE;
    }

    private static class DBManagerHolder {

        private static final DBManager INSTANCE = new DBManager();
    }


}
