package me.azhereus.awakening;

import me.azhereus.awakening.commands.ManageCommand;
import me.azhereus.awakening.commands.StatsCommand;
import me.azhereus.awakening.config.LevelConfig;
import me.azhereus.awakening.listeners.DataPersistenceListener;
import me.azhereus.awakening.listeners.LevelingListener;
import me.azhereus.awakening.listeners.ManageMenuListener;
import me.azhereus.awakening.listeners.StatsMenuListener;
import me.azhereus.awakening.scoreboard.XPScoreboard;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Awakening extends JavaPlugin implements Listener {

    // instance for main class
    private static Awakening instance;


    public static Awakening getInstance() {
        return instance;
    }

    // initialization of attributes
    public static final NamespacedKey LEVEL_KEY = new NamespacedKey("awakening", "level");
    public static final NamespacedKey EXP_KEY = new NamespacedKey("awakening", "exp");
    public static final NamespacedKey STRENGTH_KEY = new NamespacedKey("awakening", "strength");
    public static final NamespacedKey AGILITY_KEY = new NamespacedKey("awakening", "agility");
    public static final NamespacedKey ATTRIBUTES_POINTS_KEY = new NamespacedKey("awakening", "attributes");


    @Override
    public void onEnable() {
        System.out.println("Awakening Core started.");

        //config
        LevelConfig.load(this);

        // initializing commands
        getCommand("manage").setExecutor(new ManageCommand());
        getCommand("stats").setExecutor(new StatsCommand(this));
        getCommand("addPoints").setExecutor(new StatsCommand(this));

        // events and listeners
        getServer().getPluginManager().registerEvents(new ManageMenuListener(), this);
        getServer().getPluginManager().registerEvents(new DataPersistenceListener(this), this);
        getServer().getPluginManager().registerEvents(new LevelingListener(), this);
        getServer().getPluginManager().registerEvents(new StatsMenuListener(this), this);
        getServer().getPluginManager().registerEvents(this,this);


        // start interface / static initializers / etc..,
        new XPScoreboard(this).startScoreboardUpdater();

    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent ev) {

        ev.getPlayer().sendTitle(
                ChatColor.RED + "Project Awakening RPG System",
                ChatColor.GREEN + "Welcome, hunter.\nVersion: v1.0",
                20,
                100,
                20
        );

    }

    // old weird method powered by stackoverflow and AI :D
//    public <P, C> C getAttributeValue(Player p, NamespacedKey attr, PersistentDataType<P, C> type, C defaultValue) {
//        PersistentDataContainer container = p.getPersistentDataContainer();
//        // Special handling for DOUBLE: if an integer is stored, convert it.
//        if (type.equals(PersistentDataType.DOUBLE)) {
//            if (container.has(attr, PersistentDataType.INTEGER)) {
//                int storedInt = container.getOrDefault(attr, PersistentDataType.INTEGER, 0);
//                double converted = storedInt; // auto conversion to double
//                // Update the stored value to double so future calls work correctly.
//                container.set(attr, PersistentDataType.DOUBLE, converted);
//                @SuppressWarnings("unchecked")
//                C result = (C) Double.valueOf(converted);
//                return result;
//            }
//        }
//        return container.getOrDefault(attr, type, defaultValue);
//    }

    // attributes
    public <P, C> C getAttributeValue(Player p, NamespacedKey attr, PersistentDataType<P, C> type, C defaultValue) {
        return p.getPersistentDataContainer().getOrDefault(attr, type, defaultValue);
    }





}
