package me.azhereus.awakening.menus;

import me.azhereus.awakening.Awakening;
import me.azhereus.awakening.config.LevelConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class StatsMenu {

    private final Awakening plugin;
    private final Player player;
    private final Inventory inv;

    public StatsMenu(Player player, Awakening plugin) {
        this.plugin = plugin;
        this.player = player;
        inv = Bukkit.createInventory(null, 9, ChatColor.BLUE + "Level Up");
        updateInventory();
        player.openInventory(inv);
    }

    public void updateInventory() {
        int strVal = plugin.getAttributeValue(player, Awakening.STRENGTH_KEY, PersistentDataType.INTEGER, 1);
        int agiVal = plugin.getAttributeValue(player, Awakening.AGILITY_KEY, PersistentDataType.INTEGER, 1);
        inv.setItem(0, createPlayerDetailsItem());
        inv.setItem(3, createAttributeItem("Strength", strVal));
        inv.setItem(5, createAttributeItem("Agility", agiVal));
    }

    private ItemStack createAttributeItem(String name, int value) {
        Material material = Material.PAPER;
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name + " (" + value + ")");
        meta.setLore(List.of("Click to add 1 point"));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createPlayerDetailsItem() {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Player Details");

        int strength = plugin.getAttributeValue(player, Awakening.STRENGTH_KEY, PersistentDataType.INTEGER, 1);
        int agility = plugin.getAttributeValue(player, Awakening.AGILITY_KEY, PersistentDataType.INTEGER, 1);
        int level = plugin.getAttributeValue(player, Awakening.LEVEL_KEY, PersistentDataType.INTEGER, 1);
        double xp = plugin.getAttributeValue(player, Awakening.EXP_KEY, PersistentDataType.DOUBLE, 1.0);
        int attrPts = plugin.getAttributeValue(player, Awakening.ATTRIBUTES_POINTS_KEY, PersistentDataType.INTEGER, 1);
        double nextLvl = LevelConfig.getXpThreshold(level);

        List<String> lore = List.of(
                ChatColor.GRAY + "Strength: " + ChatColor.WHITE + strength,
                ChatColor.GRAY + "Agility: " + ChatColor.WHITE + agility,
                ChatColor.GRAY + "Level: " + ChatColor.WHITE + level,
                ChatColor.GRAY + "XP: " + ChatColor.WHITE + xp + "/" + ChatColor.YELLOW + nextLvl,
                ChatColor.GRAY + "Attributes: " + ChatColor.WHITE + attrPts
        );
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    // update menu, now players dont need to retype /stats all the time
    public static void updateOpenStatsMenu(Player player, Awakening plugin) {
        Inventory openInv = player.getOpenInventory().getTopInventory();
        int strVal = plugin.getAttributeValue(player, Awakening.STRENGTH_KEY, PersistentDataType.INTEGER, 1);
        int agiVal = plugin.getAttributeValue(player, Awakening.AGILITY_KEY, PersistentDataType.INTEGER, 1);
        openInv.setItem(0, createPlayerDetailsItemStatic(player, plugin));
        openInv.setItem(3, createAttributeItemStatic("Strength", strVal));
        openInv.setItem(5, createAttributeItemStatic("Agility", agiVal));
    }

    private static ItemStack createAttributeItemStatic(String name, int value) {
        Material material = Material.PAPER;
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name + " (" + value + ")");
        meta.setLore(List.of("Click to add 1 point"));
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack createPlayerDetailsItemStatic(Player player, Awakening plugin) {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Player Details");

        int strength = plugin.getAttributeValue(player, Awakening.STRENGTH_KEY, PersistentDataType.INTEGER, 1);
        int agility = plugin.getAttributeValue(player, Awakening.AGILITY_KEY, PersistentDataType.INTEGER, 1);
        int level = plugin.getAttributeValue(player, Awakening.LEVEL_KEY, PersistentDataType.INTEGER, 1);
        double xp = plugin.getAttributeValue(player, Awakening.EXP_KEY, PersistentDataType.DOUBLE, 1.0);
        int attrPts = plugin.getAttributeValue(player, Awakening.ATTRIBUTES_POINTS_KEY, PersistentDataType.INTEGER, 1);
        double nextLvl = LevelConfig.getXpThreshold(level);


        List<String> lore = List.of(
                ChatColor.GRAY + "Strength: " + ChatColor.WHITE + strength,
                ChatColor.GRAY + "Agility: " + ChatColor.WHITE + agility,
                ChatColor.GRAY + "Level: " + ChatColor.WHITE + level,
                ChatColor.GRAY + "XP: " + ChatColor.WHITE + xp + "/" + ChatColor.YELLOW + nextLvl,
                ChatColor.GRAY + "Attributes: " + ChatColor.WHITE + attrPts
        );
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
