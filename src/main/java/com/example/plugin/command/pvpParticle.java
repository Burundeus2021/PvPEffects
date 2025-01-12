package com.example.plugin.command;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;

public class pvpParticle implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;

    public pvpParticle(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Эту команду может использовать только игрок.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage("Использование: /setparticle <название_частицы>");
            return true;
        }

        String particleName = args[0].toUpperCase();
        try {
            Particle particle = Particle.valueOf(particleName);
            plugin.getConfig().set("player-particle." + player.getUniqueId(), particleName);
            plugin.saveConfig();
//            player.sendMessage(ChatColor.GREEN + "Частица " + ChatColor.WHITE + particleName + ChatColor.GREEN + " сохранена для вас.");
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.RED + "Ошибка! Частица не найдена: " + ChatColor.WHITE + particleName);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            for (Particle particle : Particle.values()) {
                completions.add(particle.name().toLowerCase());
            }
        }

        return completions;
    }

    public void playSavedParticle(Player player) {
        String particleName = plugin.getConfig().getString("player-particle." + player.getUniqueId());

        if (particleName != null) {
            try {
                Particle particle = Particle.valueOf(particleName);
                player.spawnParticle(particle, player.getLocation(), 50);
                player.sendMessage("Проигрывается сохранённая частица: " + particleName);
            } catch (IllegalArgumentException e) {
                player.sendMessage("Сохранённая частица больше не существует.");
            }
        } else {
            player.sendMessage("У вас ещё нет сохранённой частицы.");
        }
    }
}
