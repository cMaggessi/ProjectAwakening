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
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DataPersistenceListener implements Listener {

    private final Awakening plugin;

    public DataPersistenceListener(Awakening plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent ev) {
        Player p = ev.getPlayer();
        checkPlayerJoiningFirstTime(ev, p);

        PersistentDataContainer data = p.getPersistentDataContainer();
        // Create defaults for integer attributes.
        Map<NamespacedKey, Integer> defaultIntData = new HashMap<>();
        defaultIntData.put(Awakening.LEVEL_KEY, 1);
        defaultIntData.put(Awakening.STRENGTH_KEY, 1);
        defaultIntData.put(Awakening.AGILITY_KEY, 1);
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
        String playerName = player.getDisplayName();

        if (level < 1) level = 1;
        if (exp < 0) exp = 0.0;
        if (strength < 1) strength = 1;
        if (agility < 1) agility = 1;

        // write to plugin config
        String path = "players." + player.getUniqueId().toString() + ".";
        plugin.getConfig().set(path + "playerName", playerName);
        plugin.getConfig().set(path + "level", level);
        plugin.getConfig().set(path + "experience", exp);
        plugin.getConfig().set(path + "strength", strength);
        plugin.getConfig().set(path + "agility", agility);

        // save player data to config
        plugin.saveConfig();
        Bukkit.getLogger().info("Persisted data for player " + player.getName());
    }

    // if player has already joined or not send cool messages
    private static void checkPlayerJoiningFirstTime(PlayerJoinEvent ev, Player p) {
        if(ev.getPlayer().hasPlayedBefore()) {
            p.sendMessage("Welcome back "+ p.getDisplayName() + " let's level up some more! :D");
        } else {
            sendCoolFirstTimeMessage(p);
        }
    }



    private static void sendCoolFirstTimeMessage(Player p) {
        // TODO add cool array of messages, AI generated btw lol
        List<String> welcomeMessages = List.of(
                "The gates have opened, and " + p.getDisplayName() + " has entered the world of Project Awakening. Will you rise as the ultimate hunter?",
                "A new hunter has emerged! Welcome, " + p.getDisplayName() + ", to Project Awakening. The dungeons await your strength.",
                "The System has detected a new presence: " + p.getDisplayName() + ". Welcome to Project Awakening, hunter. Your journey begins now.",
                "The shadows tremble as " + p.getDisplayName() + " steps into Project Awakening. Will you conquer the gates or fall to the monsters within?",
                "Welcome, " + p.getDisplayName() + ", to Project Awakening. The Association has been notified of your arrival. Prove your worth, hunter.",
                "The mana in the air shifts as " + p.getDisplayName() + " joins Project Awakening. A new hunter has entered the fray. Good luck.",
                "The gates have chosen you, " + p.getDisplayName() + ". Welcome to Project Awakening. Will you awaken your true potential?",
                "A new challenger approaches! " + p.getDisplayName() + " has arrived in Project Awakening. The dungeons are calling, hunter.",
                "The System welcomes " + p.getDisplayName() + " to Project Awakening. Hunters, prepare for a new ally—or rival.",
                "The world of Project Awakening grows stronger with the arrival of " + p.getDisplayName() + ". Will you become the next Shadow Monarch?"
        );
        Random rand = new Random();
        String msg = welcomeMessages.get(rand.nextInt(welcomeMessages.size()));
        Bukkit.broadcastMessage(msg);
    }
}
