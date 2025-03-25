package me.azhereus.awakening.listeners;

import me.azhereus.awakening.Awakening;
import me.azhereus.awakening.config.LevelConfig;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Random;

public class LevelingListener implements Listener {


    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer data = player.getPersistentDataContainer();

        double currentExp = data.getOrDefault(Awakening.EXP_KEY, PersistentDataType.DOUBLE, 0.0);

//        player.sendMessage(ChatMessageType.SYSTEM +": Got "+ event.getAmount());

//        player.sendMessage(ChatMessageType.SYSTEM +": Multiplied "+ event.getAmount() * LevelConfig.getXpMultiplier());

        // xp multiplier
        double xpGainMult = event.getAmount() * LevelConfig.getXpMultiplier();
        double newExp = currentExp + xpGainMult;
        int currentLevel = data.getOrDefault(Awakening.LEVEL_KEY, PersistentDataType.INTEGER, 1);
        double expThreshold = LevelConfig.getXpThreshold(currentLevel);

        while (newExp >= expThreshold) {
            newExp -= expThreshold;
            currentLevel++;
            int currentPoints = data.getOrDefault(Awakening.ATTRIBUTES_POINTS_KEY, PersistentDataType.INTEGER, 0);
            data.set(Awakening.ATTRIBUTES_POINTS_KEY, PersistentDataType.INTEGER, currentPoints + LevelConfig.ATTRIBUTE_POINTS_PER_LEVEL);
            sendLvlUpMessage(player, currentLevel);
            expThreshold = LevelConfig.getXpThreshold(currentLevel);
        }

        data.set(Awakening.LEVEL_KEY, PersistentDataType.INTEGER, currentLevel);
        data.set(Awakening.EXP_KEY, PersistentDataType.DOUBLE, newExp);
    }

    private void sendLvlUpMessage(Player p, int currLvl) {
        Random rand = new Random();
        List<String> msgs = List.of(
                p.getDisplayName() + " has leveled up!\n The System acknowledges your growth.\n Your stats have increased, and the gates tremble at your power.",
                "The mana surges as " + p.getDisplayName() + " levels up!\n Your potential as a hunter grows stronger.\n Keep pushing forward!",
                "Congratulations, " + p.getDisplayName() + "! You've leveled up.\n The Association has taken note of your progress. The dungeons won't stand a chance.",
                "The shadows grow darker as " + p.getDisplayName() + " levels up.\n Your abilities have evolved.\n Will you become the next Shadow Monarch?",
                p.getDisplayName() + " has leveled up!\n The System rewards you with enhanced stats and new skills.\n The gates await your next challenge.",
                "The System announces: " + p.getDisplayName() + " has leveled up!\n Your hunter rank is rising.\n The monsters won't know what hit them.",
                "A surge of power! " + p.getDisplayName() + " has leveled up.\n Your strength, agility, and mana have increased.\n The dungeons are calling.",
                "The gates shudder as " + p.getDisplayName() + " levels up.\n Your journey as a hunter grows more formidable.\n What lies ahead?",
                p.getDisplayName() + " has leveled up! The System grants you new abilities.\n Will you use them to protect humanity or dominate the shadows?",
                "The mana within " + p.getDisplayName() + " has intensified! Level up achieved.\n The Association is impressed. Keep climbing, hunter."
        );
        String msg = msgs.get(rand.nextInt(msgs.size()));
        p.sendMessage(msg);
        p.sendMessage(ChatColor.GOLD +"Your level has increased to " + currLvl +
                ".\n You get "+ LevelConfig.ATTRIBUTE_POINTS_PER_LEVEL + " attribute points.");
        p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f);
        p.playSound(p.getLocation(), Sound.ITEM_TRIDENT_RETURN, 1.0f, 1.0f);
    }
}
