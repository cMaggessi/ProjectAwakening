package me.azhereus.awakening.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Random;

public class PlayerJoinListener implements Listener {

    @EventHandler
    private static void checkPlayerJoiningFirstTime(PlayerJoinEvent ev, Player p) {
        if(ev.getPlayer().hasPlayedBefore()) {
            p.sendMessage("Welcome back "+ p.getDisplayName() + " let's level up some more! :D");
        } else {
            sendCoolFirstTimeMessage(p);
        }
    }


    private static void sendCoolFirstTimeMessage(Player p) {
        // TODO add cool array of messages, AI generated btw lol
        List<String> welcomeMessages = List.of(
                "The gates have opened, and " + p.getDisplayName() + " has entered the world of Project Awakening. Will you rise as the ultimate hunter?",
                "A new hunter has emerged! Welcome, " + p.getDisplayName() + ", to Project Awakening. The dungeons await your strength.",
                "The System has detected a new presence: " + p.getDisplayName() + ". Welcome to Project Awakening, hunter. Your journey begins now.",
                "The shadows tremble as " + p.getDisplayName() + " steps into Project Awakening. Will you conquer the gates or fall to the monsters within?",
                "Welcome, " + p.getDisplayName() + ", to Project Awakening. The Association has been notified of your arrival. Prove your worth, hunter.",
                "The mana in the air shifts as " + p.getDisplayName() + " joins Project Awakening. A new hunter has entered the fray. Good luck.",
                "The gates have chosen you, " + p.getDisplayName() + ". Welcome to Project Awakening. Will you awaken your true potential?",
                "A new challenger approaches! " + p.getDisplayName() + " has arrived in Project Awakening. The dungeons are calling, hunter.",
                "The System welcomes " + p.getDisplayName() + " to Project Awakening. Hunters, prepare for a new ally—or rival.",
                "The world of Project Awakening grows stronger with the arrival of " + p.getDisplayName() + ". Will you become the next Shadow Monarch?"
        );
        Random rand = new Random();
        String msg = welcomeMessages.get(rand.nextInt(welcomeMessages.size()));
        Bukkit.broadcastMessage(msg);
    }
}
