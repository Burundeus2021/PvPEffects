package com.example.plugin.handler;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;

public class pvp implements Listener {
    private final JavaPlugin plugin;

    public pvp(JavaPlugin plugin) {
        this.plugin = plugin;
    }

//    @EventHandler
//    public void handlerPlayerJoinEvent(PlayerJoinEvent e){
//        Player player = e.getPlayer();
//        String uuid  = player.getUniqueId().toString();
//        String patch = String.join(".", "users", uuid, "last-join");
//        LocalDateTime TimeDateNow = LocalDateTime.now();
//        plugin.getConfig().set(patch, TimeDateNow.toString());
//        plugin.saveConfig();
//        player.sendMessage("Приятной игры!");
//    }



    @EventHandler
    public void headlerEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {

        Player player = (Player) e.getDamager();
        String uuid = player.getUniqueId().toString();
        Player player2 = (Player) e.getEntity();
        Location domagerlocation = (Location) e.getDamager().getLocation();

        String soundName = plugin.getConfig().getString("player-sound." + player.getUniqueId());
        String particleName = plugin.getConfig().getString("player-particle." + player.getUniqueId());

        Sound sound = Sound.valueOf(soundName);
        Particle particle = Particle.valueOf(particleName);

        player.playSound(domagerlocation, sound , 5, 1);
        player2.getWorld().spawnParticle(particle, player2.getLocation().add(0, 1, 0), 30, 0.7, 0.7, 0.7, 0.1);
//        player.spawnParticle(location, Particle.LAVA , 1,2);

    }

//    @EventHandler
//    public void headlerEntytyDomage(EntityDamageEvent e) {
//        Player player2 = (Player) e.getEntity();
//        player2.getWorld().spawnParticle(Particle.GLOW, player2.getLocation().add(0, 1, 0), 10, 0.5, 0.5, 0.5, 0.1);
//    }

    @EventHandler
    public void hendlerEntytyDeatch(EntityDeathEvent e) {
        Player playerdeatch = (Player) e.getEntity();

        String sounddName = plugin.getConfig().getString("player-deathsound." + playerdeatch.getUniqueId());

        Sound soundd = Sound.valueOf(sounddName);

        playerdeatch.getWorld().spawnParticle(Particle.DRIP_LAVA, playerdeatch.getLocation().add(0, 1, 0), 10, 0.5, 0.5, 0.5, 0.1);
        Location domagerlocation = (Location) e.getEntity().getLocation();
        playerdeatch.playSound(domagerlocation, soundd, 5, 1);
    }

}
//package com.example.plugin.handler;
//
//import org.bukkit.Location;
//import org.bukkit.Particle;
//import org.bukkit.Sound;
//import org.bukkit.entity.Entity;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.entity.EntityDamageByEntityEvent;
//import org.bukkit.event.entity.EntityDeathEvent;
//import org.bukkit.plugin.java.JavaPlugin;
//
//public class pvp implements Listener {
//    private final JavaPlugin plugin;
//
//    public pvp(JavaPlugin plugin) {
//        this.plugin = plugin;
//    }
//
//    @EventHandler
//    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
//        if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player)) {
//            return; // Если не игрок атакует игрока, выходим из метода
//        }
//
//        Player damager = (Player) e.getDamager();
//        Player victim = (Player) e.getEntity();
//
//        String soundName = plugin.getConfig().getString("player-sound." + damager.getUniqueId());
//        String particleName = plugin.getConfig().getString("player-particle." + damager.getUniqueId());
//
//        try {
//            if (soundName != null) {
//                Sound sound = Sound.valueOf(soundName);
//                damager.playSound(damager.getLocation(), sound, 5, 1);
//            }
//
//            if (particleName != null) {
//                Particle particle = Particle.valueOf(particleName);
//                victim.getWorld().spawnParticle(particle, victim.getLocation().add(0, 1, 0), 30, 0.7, 0.7, 0.7, 0.1);
//            }
//        } catch (IllegalArgumentException ex) {
//            damager.sendMessage("Ошибка: сохранённый звук или частица недействительны.");
//        }
//    }
//
//    @EventHandler
//    public void onEntityDeath(EntityDeathEvent e) {
//        if (!(e.getEntity() instanceof Player)) {
//            return; // Только игроки
//        }
//
//        Player player = (Player) e.getEntity();
//        String deathSoundName = plugin.getConfig().getString("player-deathsound." + player.getUniqueId());
//
//        try {
//            if (deathSoundName != null) {
//                Sound deathSound = Sound.valueOf(deathSoundName);
//                player.playSound(player.getLocation(), deathSound, 5, 1);
//            }
//            player.getWorld().spawnParticle(Particle.DRIP_LAVA, player.getLocation().add(0, 1, 0), 20, 0.5, 0.5, 0.5, 0.1);
//        } catch (IllegalArgumentException ex) {
//            player.sendMessage("Ошибка: сохранённый звук недействителен.");
//        }
//    }
//}
