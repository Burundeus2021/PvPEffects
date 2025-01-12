package com.example.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
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

import java.util.Objects;

public class ParticleGUI implements CommandExecutor, Listener {

    public ParticleGUI(Main main) {
        Bukkit.getServer().getPluginManager().registerEvents(this, main);
    }

    public ParticleGUI() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            openParticleGUI(player);
        } else {
            sender.sendMessage("Only players can use this command!");
        }
        return true;
    }

    public void openParticleGUI(Player player) {
        Inventory gui = Bukkit.createInventory(player, 36, ChatColor.DARK_GRAY + "Меню эффектов пвп");

        gui.setItem(10, createGuiItem(Material.EMERALD, ChatColor.GREEN + "Частицы изумруда"));
        gui.setItem(11, createGuiItem(Material.NOTE_BLOCK, ChatColor.DARK_RED + "Частицы нот"));
        gui.setItem(12, createGuiItem(Material.GLOW_INK_SAC, ChatColor.DARK_AQUA + "Частицы подсвечивания"));
        gui.setItem(13, createGuiItem(Material.ENDER_EYE, ChatColor.DARK_PURPLE + "Частицы эндора"));
        gui.setItem(14, createGuiItem(Material.BLAZE_POWDER, ChatColor.GOLD + "Частицы огня"));
        gui.setItem(15, createGuiItem(Material.FEATHER, ChatColor.WHITE + "Частицы облаков"));
        gui.setItem(16, createGuiItem(Material.SNOWBALL, ChatColor.WHITE + "Частицы снега"));
        gui.setItem(31, createGuiItem(Material.ARROW, ChatColor.RED + "<- НАЗАД"));

        player.openInventory(gui);
    }

    private ItemStack createGuiItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equals(ChatColor.DARK_GRAY + "Меню эффектов пвп")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || !clickedItem.hasItemMeta()) return;

            String itemName = Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName();

            if (itemName.equals(ChatColor.GREEN + "Частицы изумруда")) {
                player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setparticle VILLAGER_HAPPY");
            } else if (itemName.equals(ChatColor.DARK_RED + "Частицы нот")) {
                player.getWorld().spawnParticle(Particle.NOTE, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setparticle NOTE");
            } else if (itemName.equals(ChatColor.DARK_AQUA + "Частицы подсвечивания")) {
                player.getWorld().spawnParticle(Particle.GLOW, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setparticle GLOW");
            } else if (itemName.equals(ChatColor.DARK_PURPLE + "Частицы эндора")) {
                player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setparticle PORTAL");
            } else if (itemName.equals(ChatColor.GOLD + "Частицы огня")) {
                player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setparticle FLAME");
            } else if (itemName.equals(ChatColor.WHITE + "Частицы облаков")) {
                player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setparticle CLOUD");
            } else if (itemName.equals(ChatColor.WHITE + "Частицы снега")) {
                Bukkit.dispatchCommand(player, "setparticle SNOWBALL");
                player.getWorld().spawnParticle(Particle.SNOWFLAKE, player.getLocation(), 20);
            } else if (itemName.equals(ChatColor.RED + "<- НАЗАД")) {
                new EffectsGUI().openPvPEffectsGUI(player);
            }
        }
    }
}
