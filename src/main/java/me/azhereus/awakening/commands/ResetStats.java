package me.azhereus.awakening.commands;

import me.azhereus.awakening.Awakening;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class ResetStats implements CommandExecutor {

    public ResetStats(Awakening plugin) {}

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return true;
        }

        if (args.length == 0 || !args[0].equalsIgnoreCase("confirm")) {
            player.sendMessage(ChatColor.RED + "Are you sure you want to reset all your stats?");
            player.sendMessage(ChatColor.RED + "This will refund all your attribute points but reset your bonuses.");
            player.sendMessage(ChatColor.GOLD + "Type '/resetstats confirm' to proceed.");
            return true;
        }

        PersistentDataContainer data = player.getPersistentDataContainer();

        int healthPoints = data.getOrDefault(Awakening.HEALTH_KEY, PersistentDataType.INTEGER, 0);
        int damagePoints = data.getOrDefault(Awakening.STRENGTH_KEY, PersistentDataType.INTEGER, 0);
        int agilityPoints = data.getOrDefault(Awakening.AGILITY_KEY, PersistentDataType.INTEGER, 0);

        int totalPointsSpent = healthPoints + damagePoints + agilityPoints;

        data.set(Awakening.HEALTH_KEY, PersistentDataType.INTEGER, 0);
        data.set(Awakening.STRENGTH_KEY, PersistentDataType.INTEGER, 0);
        data.set(Awakening.AGILITY_KEY, PersistentDataType.INTEGER, 0);

        // Refund points
        int currentPoints = data.getOrDefault(Awakening.ATTRIBUTES_POINTS_KEY, PersistentDataType.INTEGER, 0);
        data.set(Awakening.ATTRIBUTES_POINTS_KEY, PersistentDataType.INTEGER, currentPoints + totalPointsSpent);

        resetPlayerAttr(player);

        player.sendMessage(ChatColor.GREEN + "Successfully reset all stats!");
        player.sendMessage(ChatColor.GREEN + "Refunded " + totalPointsSpent + " attribute points.");

        return true;
    }

    private void resetPlayerAttr(Player p) {
        if(p != null) {
            Objects.requireNonNull(p.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(20.0);
            p.setHealth(20.0);
            Objects.requireNonNull(p.getAttribute(Attribute.ATTACK_DAMAGE)).setBaseValue(1.0);
            p.setWalkSpeed(0.2f);
            p.sendMessage(ChatColor.YELLOW + "All attributes have been reset to default values.");
        }
    }
}