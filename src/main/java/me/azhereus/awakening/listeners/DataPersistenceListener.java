package me.azhereus.awakening.listeners;

import me.azhereus.awakening.Awakening;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class DataPersistenceListener implements Listener {

    private final Awakening plugin;

    public DataPersistenceListener(Awakening plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent ev) {
        Player p = ev.getPlayer();

        PersistentDataContainer data = p.getPersistentDataContainer();
        // Create defaults for integer attributes.
        Map<NamespacedKey, Integer> defaultIntData = new HashMap<>();
        defaultIntData.put(Awakening.LEVEL_KEY, 1);
        defaultIntData.put(Awakening.STRENGTH_KEY, 1);
        defaultIntData.put(Awakening.AGILITY_KEY, 1);
        defaultIntData.put(Awakening.HEALTH_KEY, 1);
        defaultIntData.put(Awakening.ATTRIBUTES_POINTS_KEY, 0);

        // Ensure each int attribute exists.
        for (Map.Entry<NamespacedKey, Integer> entry : defaultIntData.entrySet()) {
            if (!data.has(entry.getKey(), PersistentDataType.INTEGER)) {
                data.set(entry.getKey(), PersistentDataType.INTEGER, entry.getValue());
            }
        }

        // Now ensure XP is stored as a double.
        if (!data.has(Awakening.EXP_KEY, PersistentDataType.DOUBLE)) {
            data.set(Awakening.EXP_KEY, PersistentDataType.DOUBLE, 0.0);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer data = player.getPersistentDataContainer();

        int level = data.getOrDefault(Awakening.LEVEL_KEY, PersistentDataType.INTEGER, 1);
        double exp = data.getOrDefault(Awakening.EXP_KEY, PersistentDataType.DOUBLE, 0.0);
        int strength = data.getOrDefault(Awakening.STRENGTH_KEY, PersistentDataType.INTEGER, 1);
        int agility = data.getOrDefault(Awakening.AGILITY_KEY, PersistentDataType.INTEGER, 1);
        int health = data.getOrDefault(Awakening.HEALTH_KEY, PersistentDataType.INTEGER, 1);
        String playerName = player.getDisplayName();

        if (level < 1) level = 1;
        if (exp < 0) exp = 0.0;
        if (strength < 1) strength = 1;
        if (agility < 1) agility = 1;
        if (health <1 )health = 1;

        // write to plugin config
        String path = "players." + player.getUniqueId().toString() + ".";
        plugin.getConfig().set(path + "playerName", playerName);
        plugin.getConfig().set(path + "level", level);
        plugin.getConfig().set(path + "experience", exp);
        plugin.getConfig().set(path + "strength", strength);
        plugin.getConfig().set(path + "agility", agility);
        plugin.getConfig().set(path + "health", health);

        // save player data to config
        plugin.saveConfig();
        Bukkit.getLogger().info("Persisted data for player " + player.getName());
    }
}
