//package com.example.plugin.command;
//
//import com.example.plugin.Main;
//import org.bukkit.Bukkit;
//import org.bukkit.Sound;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//import org.bukkit.plugin.java.JavaPlugin;
//
//public class SoundCommandPlugin extends JavaPlugin implements CommandExecutor {
//
//    public SoundCommandPlugin(Main main) {
//    }
//
//    @Override
//    public void onEnable() {
//        // Регистрация команды в плагине
//        this.getCommand("playsound").setExecutor(this);
//    }
//
//    @Override
//    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//        if (!(sender instanceof Player)) {
//            sender.sendMessage("Эту команду может использовать только игрок.");
//            return true;
//        }
//
//        Player player = (Player) sender;
//
//        // Проверка, что аргумент введен
//        if (args.length < 1) {
//            player.sendMessage("Использование: /playsound <название_звука>");
//            return true;
//        }
//
//        String soundName = args[0].toUpperCase(); // Получаем имя звука и приводим к верхнему регистру
//        try {
//            Sound sound = Sound.valueOf(soundName); // Преобразуем строку в объект Sound
//            player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
//            player.sendMessage("Воспроизведение звука: " + soundName);
//        } catch (IllegalArgumentException e) {
//            player.sendMessage("Ошибка! Звук не найден: " + soundName);
//        }
//
//        return true;
//    }
//}
