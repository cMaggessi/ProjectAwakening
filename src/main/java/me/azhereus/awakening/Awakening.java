package me.azhereus.awakening;

import me.azhereus.awakening.commands.ManageCommand;
import me.azhereus.awakening.listeners.ManageMenuListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Awakening extends JavaPlugin {

    private static Awakening instance;


    @Override
    public void onEnable() {
        System.out.println("Awakening Core started.");
        getCommand("manage").setExecutor(new ManageCommand());
        getServer().getPluginManager().registerEvents(new ManageMenuListener(), this);
    }

    public static Awakening getInstance() {
        return instance;
    }

}
