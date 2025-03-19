package me.azhereus.awakening.config;

import me.azhereus.awakening.Awakening;
import org.bukkit.configuration.file.FileConfiguration;

public class LevelConfig {
    // multiplier for XP gain
    private static double xpMultiplier = 5.0;

    public static void load(Awakening plugin) {
//        FileConfiguration config = plugin.getConfig();
//        xpMultiplier = config.getDouble("xpMultiplier", 1.0);
//        xpThresholdMultiplier = config.getDouble("xpThresholdMultiplier", 100.0);
    }

    public static double getXpThreshold(int level) {
        // multiplier for XP threshold: necessary XP = level * xpThresholdMultiplier
        double xpThresholdMultiplier = 100.0;
        return level * xpThresholdMultiplier;
    }

    public static double getXpMultiplier() {
        return xpMultiplier;
    }

    public static void setXpMultiplier(double multiplier) {
        xpMultiplier = multiplier;
    }
}
