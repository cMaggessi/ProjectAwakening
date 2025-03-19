    package me.azhereus.awakening.scoreboard;

    import me.azhereus.awakening.Awakening;
    import me.azhereus.awakening.config.LevelConfig;
    import net.md_5.bungee.api.ChatMessageType;
    import net.md_5.bungee.api.chat.TextComponent;
    import org.bukkit.Bukkit;
    import org.bukkit.ChatColor;
    import org.bukkit.entity.Player;
    import org.bukkit.persistence.PersistentDataType;

    public class XPScoreboard {

        private final Awakening plugin;

        public XPScoreboard(Awakening plugin) {
            this.plugin = plugin;
        }

        public void startScoreboardUpdater() {
            Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    updateXPActionBar(player);
                }
            }, 0L, 20L);
        }

        private void updateXPActionBar(Player player) {
            int level = plugin.getAttributeValue(player, Awakening.LEVEL_KEY, PersistentDataType.INTEGER, 1);
            double xp = plugin.getAttributeValue(player, Awakening.EXP_KEY, PersistentDataType.DOUBLE, 0.0);
            double necessaryXp = LevelConfig.getXpThreshold(level);
            String message = ChatColor.WHITE + "Level: " + level
                    + ChatColor.GRAY + " | "
                    + ChatColor.GREEN + "XP: " + xp
                    + ChatColor.RED + "/"
                    + ChatColor.YELLOW + necessaryXp;
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
        }
    }
