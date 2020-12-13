package highlands.highlandscore.utilities;

import highlands.highlandscore.HighlandsCore;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public class AdvancedLogger {
    //HighlandsCore
    private HighlandsCore highlandsCore;

    //Logs
    public File log_folder;
    public File log_file;

    public AdvancedLogger(HighlandsCore highlandsCore) {
        this.highlandsCore = highlandsCore;
        setupLogFiles();
    }

    private void setupLogFiles() {
        //Setup Log Files
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        String time = dateFormat.format(date);
        log_folder = new File(highlandsCore.getDataFolder().toPath() + "/Logs");
        log_file = new File(log_folder.toPath() + "/log_" + time + ".txt");
    }

    public void Log(Level level, String plugin, String message) {
        //Log to Console if Wanted by config
        if (highlandsCore.getSettings().consoleLog()) {
            Bukkit.getLogger().log(level, "[" + plugin + "] " + message);
        }

        //Log to File if Wanted by config
        if (highlandsCore.getSettings().fileLog())
        {
            try {
                if (!log_folder.exists()) {
                    log_folder.mkdir();
                    Bukkit.getConsoleSender().sendMessage(log_folder.toString());
                }

                if (!log_file.exists()) {
                    log_file.createNewFile();
                    return;
                }

                FileWriter fw = new FileWriter(log_file, true);
                PrintWriter pw = new PrintWriter(fw);
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");
                String time = dateFormat.format(date);
                pw.println(time + " : " + "[" + level + "] " + "[" + plugin + "] " + message);
                pw.flush();
                pw.close();


            }
            catch (Exception e) {
                e.printStackTrace();
                Log(Level.WARNING, "HighlandsCore", "Log file error: " + e);
            }
        }
    }
}
