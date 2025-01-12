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

public class DeadSoundGUI implements CommandExecutor, Listener {

    public DeadSoundGUI(Main main) {
        Bukkit.getServer().getPluginManager().registerEvents(this, main);
    }

    public DeadSoundGUI() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            openDeadSoundGUI(player);
        } else {
            sender.sendMessage("Only players can use this command!");
        }
        return true;
    }

    public void openDeadSoundGUI(Player player) {
        Inventory gui = Bukkit.createInventory(player, 36, ChatColor.DARK_GRAY + "Меню эффектов пвп");

        gui.setItem(10, createGuiItem(Material.NETHER_STAR, ChatColor.WHITE + "Звук удара молнии"));
        gui.setItem(11, createGuiItem(Material.HEART_OF_THE_SEA, ChatColor.WHITE + "Глубокий подводный звук"));
        gui.setItem(12, createGuiItem(Material.GHAST_TEAR, ChatColor.DARK_AQUA + "Крик гаста"));
        gui.setItem(13, createGuiItem(Material.ANVIL, ChatColor.DARK_PURPLE + "Звук накавальни"));
        gui.setItem(14, createGuiItem(Material.DRAGON_HEAD, ChatColor.WHITE + "Звук смерти дракона"));
        gui.setItem(15, createGuiItem(Material.WITHER_SKELETON_SKULL, ChatColor.WHITE + "Звук смерти визера"));
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

            if (itemName.equals(ChatColor.WHITE + "Звук удара молнии")) {
                player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setdeathsound ENTITY_LIGHTNING_BOLT_IMPACT");
            } else if (itemName.equals(ChatColor.WHITE + "Глубокий подводный звук")) {
                player.getWorld().spawnParticle(Particle.NOTE, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setdeathsound ENTITY_ELDER_GUARDIAN_DEATH");
            } else if (itemName.equals(ChatColor.DARK_AQUA + "Крик гаста")) {
                player.getWorld().spawnParticle(Particle.GLOW, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setdeathsound ENTITY_GHAST_SCREAM");
            } else if (itemName.equals(ChatColor.DARK_PURPLE + "Звук накавальни")) {
                player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setdeathsound BLOCK_ANVIL_LAND");
            } else if (itemName.equals(ChatColor.WHITE + "Звук смерти дракона")) {
                player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setdeathsound ENTITY_ENDER_DRAGON_DEATH");
            } else if (itemName.equals(ChatColor.WHITE + "Звук смерти визера")) {
                player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 20);
                Bukkit.dispatchCommand(player, "setdeathsound ENTITY_WITHER_DEATH");
            } else if (itemName.equals(ChatColor.WHITE + "Глухой удар")) {
                Bukkit.dispatchCommand(player, "setdeathsound ENTITY_ZOMBIE_ATTACK_IRON_DOOR");
                player.getWorld().spawnParticle(Particle.SNOWFLAKE, player.getLocation(), 20);
            } else if (itemName.equals(ChatColor.RED + "<- НАЗАД")) {
                new EffectsGUI().openPvPEffectsGUI(player);
            }
        }
    }
}
