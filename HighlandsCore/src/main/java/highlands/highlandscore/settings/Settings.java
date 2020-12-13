package highlands.highlandscore.settings;

import highlands.highlandscore.HighlandsCore;

public class Settings {
    private HighlandsCore highlandsCore;

    public Settings(HighlandsCore highlandsCore) {
        this.highlandsCore = highlandsCore;
    }

    public boolean consoleLog() {
        return highlandsCore.getConfig().getBoolean("console-log");
    }

    public boolean fileLog() {
        return highlandsCore.getConfig().getBoolean("file-log");
    }

    /*
     * Mysql
     */

    public boolean getUseMysql() {
        return highlandsCore.getConfig().getBoolean("use-mysql");
    }

    public String getMysqlHost() {
        return highlandsCore.getConfig().getString("host");
    }

    public String getMysqlDatabase() {
        return highlandsCore.getConfig().getString("database");
    }

    public String getMysqlUsername() {
        return highlandsCore.getConfig().getString("username");
    }

    public String getMysqlPassword() {
        return highlandsCore.getConfig().getString("password");
    }

    public int getMysqlPort() {
        return highlandsCore.getConfig().getInt("port");
    }

    //TODO: Add settings
}
