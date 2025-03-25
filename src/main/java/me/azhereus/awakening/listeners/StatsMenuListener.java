package me.azhereus.awakening.listeners;

import me.azhereus.awakening.Awakening;
import me.azhereus.awakening.menus.StatsMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

import static me.azhereus.awakening.config.LevelConfig.*;

public class StatsMenuListener implements Listener {

    private final Awakening plugin;

    public StatsMenuListener(Awakening plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent ev) {
        if (!ev.getView().getTitle().equals(ChatColor.BLUE + "Level Up")) {
            return;
        }
        ev.setCancelled(true);

        if (ev.getClickedInventory() == null) {
            return;
        }

        Player player = (Player) ev.getWhoClicked();
        ItemStack clickedItem = ev.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        PersistentDataContainer playerData = player.getPersistentDataContainer();
        int attrPoints = playerData.getOrDefault(Awakening.ATTRIBUTES_POINTS_KEY, PersistentDataType.INTEGER, 0);
        if (attrPoints <= 0) {
            player.sendMessage(ChatColor.RED + "You do not have any attribute points to spend.");
            return;
        }

        String displayName = Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName();
        System.out.println(displayName);

        // gets "agility" or "strength"
        String attributeKey = displayName.toLowerCase().split(" ")[0];
        switch (attributeKey) {
            case "strength":
                updatePlayerStat(player, playerData, Awakening.STRENGTH_KEY, attrPoints);
                updateBaseDmg(player, playerData.getOrDefault(Awakening.STRENGTH_KEY, PersistentDataType.INTEGER, 1));
                break;
            case "agility":
                updatePlayerStat(player, playerData, Awakening.AGILITY_KEY, attrPoints);
                updateAgility(player, playerData.getOrDefault(Awakening.AGILITY_KEY, PersistentDataType.INTEGER, 1));
                break;
            case "health":
                updatePlayerStat(player, playerData, Awakening.HEALTH_KEY, attrPoints);
                updateHealth(player, playerData.getOrDefault(Awakening.HEALTH_KEY, PersistentDataType.INTEGER, 1));
                break;
            default:
                player.sendMessage(ChatColor.RED + "Invalid attribute selected.");
                break;
        }

        StatsMenu.updateOpenStatsMenu(player, plugin);

    }

    private int updatePlayerStat(Player player, PersistentDataContainer data, NamespacedKey key, int attrPoints) {
        int current = data.getOrDefault(key, PersistentDataType.INTEGER, 0);
        int newValue = current + 1;
        data.set(key, PersistentDataType.INTEGER, newValue);
        System.out.println(key);
        System.out.println(data.getOrDefault(Awakening.STRENGTH_KEY, PersistentDataType.INTEGER, 0));
        data.set(Awakening.ATTRIBUTES_POINTS_KEY, PersistentDataType.INTEGER, attrPoints - 1);

        player.sendMessage(ChatColor.GREEN + "You have increased your " + key.getKey() + " stat by 1 point!");
        player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1.0f, 1.0f);

        return newValue;
    }

    private void updateHealth(Player player, int level) {
        double baseHealth = 20.0;
        double newMaxHealth = baseHealth * (1 + level * HEALTH_PERCENT_INCREASE);
        Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(newMaxHealth);
        player.sendMessage(ChatColor.GOLD + String.format("Health increased to " + "%.1f", newMaxHealth/2) + "!");
    }

    private void updateBaseDmg(Player player, int level) {
        double baseDamage = 1.0;
        double newDamage = baseDamage * (1 + level * STRENGTH_PERCENT_INCREASE);
        Objects.requireNonNull(player.getAttribute(Attribute.ATTACK_DAMAGE)).setBaseValue(newDamage);
        player.sendMessage(ChatColor.GOLD + String.format("Damage increased to " + "%.1f", newDamage) + "!");
    }

    private void updateAgility(Player player, int level) {
        float baseSpeed = 0.2f;
        float newSpeed = baseSpeed + (baseSpeed * level * (float)AGILITY_PERCENT_INCREASE);
        newSpeed = Math.min(newSpeed, 1.0f);
        player.setWalkSpeed(newSpeed);
        player.sendMessage(ChatColor.GOLD + String.format("Movement speed increased to " + "%.1f", newSpeed) + "!");
    }
}
