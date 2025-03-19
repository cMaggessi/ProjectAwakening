package me.azhereus.awakening.commands;

import me.azhereus.awakening.Awakening;
import me.azhereus.awakening.menus.StatsMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {
    private final Awakening plugin;
    public StatsCommand(Awakening plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // handel stats command
        if (command.getName().equalsIgnoreCase("stats")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You must be a player to run this command.");
                return true;
            }
            Player p = (Player) sender;
            new StatsMenu(p, plugin);
            return true;
        }
        // handle /addPoints <target> <points>
        else if (command.getName().equalsIgnoreCase("addPoints")) {
            if (args.length != 2) {
                sender.sendMessage(ChatColor.RED + "Wrong usage of command.");
                sender.sendMessage(ChatColor.RED + "Example: /addPoints <player> <numberOfPoints>");
                return true;
            }

            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }

            int pointsToAdd;
            try {
                pointsToAdd = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid number of points: " + args[1]);
                return true;
            }

            int currentPoints = target.getPersistentDataContainer().getOrDefault(
                    Awakening.ATTRIBUTES_POINTS_KEY,
                    org.bukkit.persistence.PersistentDataType.INTEGER,
                    0);
            target.getPersistentDataContainer().set(
                    Awakening.ATTRIBUTES_POINTS_KEY,
                    org.bukkit.persistence.PersistentDataType.INTEGER,
                    currentPoints + pointsToAdd);

            sender.sendMessage(ChatColor.GREEN + "Added " + pointsToAdd + " attribute points to " + target.getName() + ".");
            target.sendMessage(ChatColor.GREEN + "You have received " + pointsToAdd + " attribute points.");
            return true;
        }
        return false;
    }
}