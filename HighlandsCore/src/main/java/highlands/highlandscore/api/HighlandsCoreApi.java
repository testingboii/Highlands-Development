package highlands.highlandscore.api;

import highlands.highlandscore.HighlandsCore;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.SimpleServicesManager;

import java.util.logging.Level;

public class HighlandsCoreApi {
    //EtherealCore
    private HighlandsCore highlandsCore;

    public HighlandsCoreApi(HighlandsCore highlandsCore) {
        this.highlandsCore = highlandsCore;

        //Registers the API Service to be used in other plugins
        registerService();
    }

    private void registerService() {
        highlandsCore.getServer().getServicesManager().register(HighlandsCoreApi.class, this, highlandsCore, ServicePriority.High);
    }
}
