package me.azhereus.awakening.listeners;

import me.azhereus.awakening.Awakening;
import me.azhereus.awakening.services.ManageService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


public class ManageMenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent ev) {
        if (!ev.getView().getTitle().equals(ChatColor.GOLD + "Manage player")) {
            ev.setCancelled(true);
            return;
        }

        ev.setCancelled(true);

        if (ev.getCurrentItem() == null) {
            return;
        }

        Player p = (Player) ev.getWhoClicked();
        Player target = Bukkit.getPlayer(ev.getView().getItem(22).getItemMeta().getDisplayName());

        if (target == null) {
            p.closeInventory();
            p.sendMessage("Target is offline.");
            return;
        }

        switch (ev.getCurrentItem().getType()) {
            case RED_DYE -> {
                p.closeInventory();
                p.sendMessage("Target has been healed!!");
                target.setHealth(20);
            }
            case IRON_SWORD -> {
                p.closeInventory();
                p.sendMessage("Target has been killed!!");
                target.setHealth(0);
            }
            case ZOMBIE_HEAD -> {
                p.closeInventory();
                p.sendMessage("Zombie spawned at target's location!!");
                target.getWorld().spawnEntity(target.getLocation(), EntityType.ZOMBIE);
            }
            case NETHER_STAR -> {
                if (!ManageService.teleportRandomLocation(p, target)) {
                    p.sendMessage(ChatColor.WHITE + "Sending" + ChatColor.RED + target.getDisplayName() + ChatColor.WHITE + "to a random location...");
                    Bukkit.getScheduler().runTaskLater(Awakening.getInstance(), () -> {
                        p.sendMessage("This might take a while... Hang tight my sexo!!");
                    }, 20L);
                    p.closeInventory();
                }
            }
            case ENDER_PEARL -> {
                p.sendMessage("Teleporting to " + ChatColor.RED + target.getDisplayName());
                p.teleport(target);
                p.closeInventory();
            }
        }
    }
}