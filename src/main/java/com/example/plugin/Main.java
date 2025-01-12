package com.example.plugin;

import com.example.plugin.command.pvpParticle;
import com.example.plugin.command.deathSound;
import com.example.plugin.command.kickSound;
import com.example.plugin.handler.pvp;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
//        saveDefaultConfig();
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new pvp(this), this);
        Objects.requireNonNull(getCommand("setdeathsound")).setExecutor(new deathSound(this));
        Objects.requireNonNull(getCommand("pvpeffects")).setExecutor(new EffectsGUI(this));
        Objects.requireNonNull(getCommand("guiparticle")).setExecutor(new ParticleGUI(this));
        Objects.requireNonNull(getCommand("guisound")).setExecutor(new SoundGUI(this));
        Objects.requireNonNull(getCommand("guideathsound")).setExecutor(new DeadSoundGUI(this));
        Objects.requireNonNull(getCommand("setsound")).setExecutor(new kickSound(this));
        Objects.requireNonNull(getCommand("setparticle")).setExecutor(new pvpParticle(this));
    }

    public void toggleCheck(Player admin, Player playerToCheck) {
    }
}
