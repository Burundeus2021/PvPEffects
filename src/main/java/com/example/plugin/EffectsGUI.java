package com.example.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EffectsGUI implements CommandExecutor, Listener {

    private Object plugin;

    public EffectsGUI(Main main) {
        this.plugin = main;
        // Регистрируем слушатель событий
        Bukkit.getServer().getPluginManager().registerEvents(this, main);
    }

    public EffectsGUI() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            openPvPEffectsGUI(player);
        } else {
            sender.sendMessage("Only players can use this command!");
        }
        return true;
    }

    public void openPvPEffectsGUI(Player player) {
        Inventory gui = Bukkit.createInventory(player, 36, ChatColor.DARK_GRAY + "Меню еффектов пвп");

        ItemStack soundItem = createGuiItem(Material.NOTE_BLOCK, ChatColor.AQUA + "Звуки удара ♪", " ▶ Нажмите для настройки.");
        ItemStack particleItem = createGuiItem(Material.BLAZE_POWDER, ChatColor.YELLOW + "Партиклы при ударе ❄", " ▶ Нажмите для настройки.");
        ItemStack deathSoundItem = createGuiItem(Material.SKELETON_SKULL, ChatColor.DARK_RED + "Звук при смерти ☠", " ▶ Нажмите для настройки.");
        ItemStack close = createGuiItem(Material.BARRIER, ChatColor.RED + "Закрыть", "");

        gui.setItem(11, soundItem);
        gui.setItem(13, particleItem);
        gui.setItem(15, deathSoundItem);
        gui.setItem(31, close);

        player.openInventory(gui);
    }

    private ItemStack createGuiItem(Material material, String name, String description) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        // Добавляем описание (lore)
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "");
        lore.add(ChatColor.AQUA + " " + ChatColor.GRAY + description);// Добавляем описание с серым цветом
        lore.add(ChatColor.GRAY + "");
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        // Проверяем, что инвентарь - это меню "Меню еффектов пвп"
        if (event.getView().getTitle().equals(ChatColor.DARK_GRAY + "Меню еффектов пвп")) {
            event.setCancelled(true);  // Отменяем любое действие, если игрок пытается забрать предмет

            // Проверяем, на какой предмет был клик
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem == null || !clickedItem.hasItemMeta()) return;  // Если предмет пустой или без мета-данных

            String itemName = clickedItem.getItemMeta().getDisplayName();

            if (itemName.equals(ChatColor.AQUA + "Звуки удара ♪")) {
                // Выполнить действие для настройки звука удара
                new SoundGUI().openSoundGUI(player);
                // Ваш код для настройки звука удара
            } else if (itemName.equals(ChatColor.YELLOW + "Партиклы при ударе ❄")) {
                // Выполнить действие для настройки частиц при ударе
                // Ваш код для настройки частиц
                new ParticleGUI().openParticleGUI(player);
            } else if (itemName.equals(ChatColor.DARK_RED + "Звук при смерти ☠")) {
                // Выполнить действие для настройки звука при смерти
                // Ваш код для настройки звука при смерти
                new DeadSoundGUI().openDeadSoundGUI(player);
            } else if (itemName.equals(ChatColor.RED + "Закрыть")) {
                // Закрыть меню
                player.closeInventory();
            }
        }
    }

}
