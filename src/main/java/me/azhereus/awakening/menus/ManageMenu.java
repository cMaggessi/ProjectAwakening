package me.azhereus.awakening.menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ManageMenu {

    public ManageMenu(Player player, Player target) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Manage player");

        ItemStack targetHead = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemMeta targetHeadMeta = targetHead.getItemMeta();

        if (targetHeadMeta != null) {
            targetHeadMeta.setDisplayName(target.getDisplayName());
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.BLUE + "This is the target of " + target.getDisplayName());
            lore.add(ChatColor.WHITE + "Health: " + String.format("%.2f", target.getHealth()));
            lore.add(ChatColor.WHITE + "Level: " + target.getLevel()+ "/??????");
            targetHeadMeta.setLore(lore);
            targetHead.setItemMeta(targetHeadMeta);
        }
        inv.setItem(22, targetHead);

        ItemStack heal = new ItemStack(Material.RED_DYE, 1);
        ItemMeta healMeta = heal.getItemMeta();
        if (healMeta != null) {
            healMeta.setDisplayName(ChatColor.GOLD + "Heals target.");
            healMeta.setLore(List.of(ChatColor.BLUE + "This heals " + target.getDisplayName()));
            heal.setItemMeta(healMeta);
        }
        inv.setItem(11, heal);

        ItemStack kill = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta killMeta = kill.getItemMeta();
        if (killMeta != null) {
            killMeta.setDisplayName(ChatColor.GOLD + "Kills target instantaneously.");
            killMeta.setLore(List.of(ChatColor.BLUE + "This kills " + target.getDisplayName()));
            kill.setItemMeta(killMeta);
        }
        inv.setItem(13, kill);

        ItemStack zombie = new ItemStack(Material.ZOMBIE_HEAD, 1);
        ItemMeta zombieMeta = zombie.getItemMeta();
        if (zombieMeta != null) {
            zombieMeta.setDisplayName(ChatColor.GOLD + "Zombie");
            zombieMeta.setLore(List.of(ChatColor.BLUE + "Spawn a zombie at " + target.getDisplayName() + "'s location."));
            zombie.setItemMeta(zombieMeta);
        }
        inv.setItem(15, zombie);

        ItemStack teleport = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta teleportMeta = teleport.getItemMeta();
        if(teleportMeta != null) {
            teleportMeta.setDisplayName(
                    ChatColor.GOLD + "Teleport");
            teleportMeta.setLore(List.of(ChatColor.LIGHT_PURPLE + "Teleport " + target.getDisplayName()+ " to a random location!"));
            teleport.setItemMeta(teleportMeta);
        }
        inv.setItem(17, teleport);


        ItemStack teleportTo = new ItemStack(Material.ENDER_PEARL, 1);
        ItemMeta teleportToMeta = teleport.getItemMeta();
        if(teleportMeta != null) {
            List<String> lore = new ArrayList<>();
            teleportToMeta.setDisplayName(ChatColor.GOLD + "Teleports you to the designated player.");
            lore.add(ChatColor.LIGHT_PURPLE + "Wow, this is a handy feature!");
            teleportToMeta.setLore(lore);
            teleportTo.setItemMeta(teleportToMeta);
        }
        inv.setItem(8, teleportTo);

        player.openInventory(inv);
    }
}