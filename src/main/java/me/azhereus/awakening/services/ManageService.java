package me.azhereus.awakening.services;

import me.azhereus.awakening.Awakening;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class ManageService {

    public static boolean teleportRandomLocation(Player p, Player target) {
        p.sendMessage(ChatColor.WHITE + "Sending" + ChatColor.RED + target.getDisplayName() + ChatColor.WHITE + "to a random location...");
        Bukkit.getScheduler().runTaskLater(Awakening.getInstance(), () -> {
            p.sendMessage("This might take a while...");
        }, 40L);

        World w = target.getWorld();
        Random random = new Random();

        int minX = -10000;
        int maxX = 10000;
        int minZ = -10000;
        int maxZ = 10000;

        int maxAttempts = 100;
        int attempts = 0;

        Location randomLocation = null;
        List<Material> setOfLeaves = List.of(
                Material.ACACIA_LEAVES,
                Material.BIRCH_LEAVES,
                Material.AZALEA_LEAVES,
                Material.CHERRY_LEAVES,
                Material.DARK_OAK_LEAVES,
                Material.SPRUCE_LEAVES
        );

        while (attempts < maxAttempts) {
            int x = random.nextInt(maxX - minX + 1) + minX;
            int z = random.nextInt(maxZ - minZ + 1) + minZ;

            int y = w.getHighestBlockYAt(x, z);
            randomLocation = new Location(w, x, y, z);

            Biome biome = w.getBlockAt(x, y - 1, z).getBiome();

            if (biome == Biome.PLAINS || biome == Biome.FOREST || biome == Biome.SAVANNA) {
                Block blockBelow = w.getBlockAt(x, y - 1, z);

                if (blockBelow.getType().isSolid() &&
                        blockBelow.getType() != Material.LAVA &&
                        blockBelow.getType() != Material.WATER &&
                        !setOfLeaves.contains(blockBelow.getType())) {
                    break;
                }
            }
            attempts++;
        }

        if (attempts >= maxAttempts) {
            p.sendMessage("Failed to find a valid location for " + target.getDisplayName() + ".");
            return false;
        }

        target.teleport(randomLocation);

        target.sendMessage("You have been teleported to a new location. The biome is " + randomLocation.getBlock().getBiome());

        String coordinates = String.format("§a[%d, %d, %d]", randomLocation.getBlockX(), randomLocation.getBlockY(), randomLocation.getBlockZ());
        TextComponent coordsMessage = new TextComponent(coordinates);

        coordsMessage.setClickEvent(
                new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
                "/tp " + randomLocation.getBlockX() + " " + randomLocation.getBlockY() + " " + randomLocation.getBlockZ()));

        coordsMessage.setHoverEvent(
                new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new Text("Click to teleport to " + coordinates)));

        p.spigot().sendMessage(
                new TextComponent("The player " + target.getDisplayName() + " has been teleported to: "),
                coordsMessage);

        return true;
    }
}