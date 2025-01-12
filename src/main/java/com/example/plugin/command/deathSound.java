package com.example.plugin.command;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;

public class deathSound implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;

    public deathSound(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Эту команду может использовать только игрок.");
            return true;
        }

        Player player = (Player) commandSender;

        if (args.length < 1) {
            player.sendMessage("Использование: /setsound <название_звука>");
            return true;
        }

        String soundName = args[0].toUpperCase();
        try {
            Sound.valueOf(soundName);
            plugin.getConfig().set("player-deathsound." + player.getUniqueId(), soundName);
            plugin.saveConfig();
//            player.sendMessage(ChatColor.GREEN + "Звук " + ChatColor.WHITE + soundName + ChatColor.GREEN + " сохранён для вас.");
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.RED + "Ошибка! Звук не найден: " + ChatColor.WHITE + soundName);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            for (Sound sound : Sound.values()) {
                completions.add(sound.name().toLowerCase());
            }
        }

        return completions;
    }

    public void playSavedSound(Player player) {
        String soundName = plugin.getConfig().getString("player-deathsound." + player.getUniqueId());

        if (soundName != null) {
            try {
                Sound sound = Sound.valueOf(soundName);
                player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
                player.sendMessage("Проигрывается сохранённый звук: " + soundName);
            } catch (IllegalArgumentException e) {
                player.sendMessage("Сохранённый звук больше не существует.");
            }
        } else {
            player.sendMessage("У вас ещё нет сохранённого звука.");
        }
    }
}
