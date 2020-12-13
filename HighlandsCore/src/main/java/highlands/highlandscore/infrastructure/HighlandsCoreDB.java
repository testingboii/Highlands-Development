package highlands.highlandscore.infrastructure;

import highlands.highlandscore.HighlandsCore;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

public class HighlandsCoreDB {
    //HighlandsCore
    private HighlandsCore highlandsCore;
    
    //Mysql
    Connection connection;
    String host, database, username, password;
    int port;

    //SQLite
    File sqliteFile = new File("plugins/HighlandsCore/HighlandsCore.db");

    public void setupMysql(HighlandsCore highlandsCore) {
        this.highlandsCore = highlandsCore;

        //Load the Mysql Config
        host = highlandsCore.getSettings().getMysqlHost();
        database = highlandsCore.getSettings().getMysqlDatabase();
        username = highlandsCore.getSettings().getMysqlUsername();
        password = highlandsCore.getSettings().getMysqlPassword();
        port = highlandsCore.getSettings().getMysqlPort();
    }

    public void setupSQLite(HighlandsCore highlandsCore) {
        this.highlandsCore = highlandsCore;
    }


    // connect
    public void connect() throws SQLException {
        if (!isConnected()) {
            try {
                if (highlandsCore.getSettings().getUseMysql()) {
                    connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                    PreparedStatement ps = this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " +
                            "PlayerData (Name VARCHAR(100)," +
                            "UUID VARCHAR(100)," +
                            "PRIMARY KEY (Name))");
                    ps.executeUpdate();
                    highlandsCore.getAdvancedLogger().Log(Level.INFO, "HighlandsCore", "Main MySQL Server Database has been Connected!");
                }
                else {
                    connection = DriverManager.getConnection("jdbc:sqlite:" + sqliteFile.getAbsolutePath());
                    PreparedStatement ps = this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " +
                            "PlayerData (Name VARCHAR(100)," +
                            "UUID VARCHAR(100)," +
                            "PRIMARY KEY (Name))");
                    ps.executeUpdate();
                    highlandsCore.getAdvancedLogger().Log(Level.INFO, "HighlandsCore", "Main SQLite Database has been Connected!");
                }


            } catch (SQLException e) {
                highlandsCore.getAdvancedLogger().Log(Level.SEVERE, "HighlandsCore", "Couldnt make new PlayerData Table or Connect to the Database: " + e);
            }
        }
    }

    // disconnect
    public void disconnect() throws SQLException {
        if (isConnected()) {
            try {
                connection.close();
                highlandsCore.getAdvancedLogger().Log(Level.INFO, "HighlandsCore", "Database has been disconnected!");
            } catch (SQLException e) {
                highlandsCore.getAdvancedLogger().Log(Level.SEVERE, "HighlandsCore", "Couldnt disconnect from Database: " + e);
            }
        }
    }

    // isConnected
    public boolean isConnected() throws SQLException {
        if (connection == null) return false;
        if (connection.isClosed()) {
            highlandsCore.getAdvancedLogger().Log(Level.SEVERE, "HighlandsCore", "Database connection was closed. Reconnecting to Database.");
            return false;
        }
        return true;
    }

    // getConnection
    public Connection getConnection() {
        return connection;
    }
}
