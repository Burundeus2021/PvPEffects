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

public class SoundGUI implements CommandExecutor, Listener {

    public SoundGUI(Main main) {
        Bukkit.getServer().getPluginManager().registerEvents(this, main);
    }

    public SoundGUI() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            openSoundGUI(player);
        } else {
            sender.sendMessage("Only players can use this command!");
        }
        return true;
    }

    public void openSoundGUI(Player player) {
        Inventory gui = Bukkit.createInventory(player, 36, ChatColor.DARK_GRAY + "Меню эффектов пвп");

        gui.setItem(10, createGuiItem(Material.PANDA_SPAWN_EGG, ChatColor.WHITE + "Чих панды"));
        gui.setItem(11, createGuiItem(Material.CAT_SPAWN_EGG, ChatColor.WHITE + "Шипние кошки"));
        gui.setItem(12, createGuiItem(Material.EXPERIENCE_BOTTLE, ChatColor.DARK_AQUA + "Звук опыта"));
        gui.setItem(13, createGuiItem(Material.EVOKER_SPAWN_EGG, ChatColor.DARK_PURPLE + "Звук атаки илюзиониста"));
        gui.setItem(14, createGuiItem(Material.EVOKER_SPAWN_EGG, ChatColor.WHITE + "Звук закленания илюзиониста"));
        gui.setItem(15, createGuiItem(Material.WITHER_SKELETON_SKULL, ChatColor.WHITE + "Звук визера"));
        gui.setItem(16, createGuiItem(Material.IRON_BLOCK, ChatColor.WHITE + "Глухой удар"));
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

            if (itemName.equals(ChatColor.WHITE + "Чих панды")) {
                player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setsound ENTITY_PANDA_SNEEZE");
            } else if (itemName.equals(ChatColor.WHITE + "Шипние кошки")) {
                player.getWorld().spawnParticle(Particle.NOTE, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setsound ENTITY_CAT_HISS");
            } else if (itemName.equals(ChatColor.DARK_AQUA + "Звук опыта")) {
                player.getWorld().spawnParticle(Particle.GLOW, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setsound entity_experience_orb_pickup");
            } else if (itemName.equals(ChatColor.DARK_PURPLE + "Звук атаки илюзиониста")) {
                player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setsound ENTITY_EVOKER_PREPARE_ATTACK");
            } else if (itemName.equals(ChatColor.WHITE + "Звук закленания илюзиониста")) {
                player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setsound ENTITY_EVOKER_CAST_SPELL");
            } else if (itemName.equals(ChatColor.WHITE + "Звук визера")) {
                player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setsound ENTITY_WITHER_SHOOT");
            } else if (itemName.equals(ChatColor.WHITE + "Глухой удар")) {
                Bukkit.dispatchCommand(player, "setsound ENTITY_ZOMBIE_ATTACK_IRON_DOOR");
                player.getWorld().spawnParticle(Particle.SNOWFLAKE, player.getLocation(), 20);
            } else if (itemName.equals(ChatColor.RED + "<- НАЗАД")) {
                new EffectsGUI().openPvPEffectsGUI(player);
            }
        }
    }
}
