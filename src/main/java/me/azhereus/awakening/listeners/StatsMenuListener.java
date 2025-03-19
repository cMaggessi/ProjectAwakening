package me.azhereus.awakening.listeners;

import me.azhereus.awakening.Awakening;
import me.azhereus.awakening.menus.StatsMenu;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.Objects;

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
            case "agility":
                updatePlayerStat(player, playerData, Awakening.AGILITY_KEY, attrPoints);
                break;
            case "strength":
                updatePlayerStat(player, playerData, Awakening.STRENGTH_KEY, attrPoints);
                break;
            default:
                player.sendMessage(ChatColor.RED + "Invalid attribute selected.");
                break;
        }

        StatsMenu.updateOpenStatsMenu(player, plugin);

    }

    private void updatePlayerStat(Player player, PersistentDataContainer data, NamespacedKey key, int attrPoints) {
        int current = data.getOrDefault(key, PersistentDataType.INTEGER, 1);
        data.set(key, PersistentDataType.INTEGER, current + 1);
        data.set(Awakening.ATTRIBUTES_POINTS_KEY, PersistentDataType.INTEGER, attrPoints - 1);

        player.sendMessage(ChatColor.GREEN + "You have increased your " + key.getKey() + " stat by 1 point!");
        player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1.0f, 1.0f);
    }
}
