package me.azhereus.awakening.commands;

import me.azhereus.awakening.menus.ManageMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ManageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        // /manage <player> <args>
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }

        Player p = (Player) sender;

        if(args.length != 1) {
            p.sendMessage(ChatColor.RED + "Use: /manage <player>");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);

        if(target == null) {
            p.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        new ManageMenu(p, target);


        return true;
    }
}