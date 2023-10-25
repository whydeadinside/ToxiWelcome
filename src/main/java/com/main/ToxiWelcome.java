package com.main;

import com.main.api.HexUtility;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class ToxiWelcome extends JavaPlugin implements Listener {

    private FileConfiguration config = getConfig();
    private List<String> message;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("&fDebugger:  &fПлагин сново &aвключен ");
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);

        message = config.getStringList("message");
    }
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("&fDebugger:  &fПлагин сново &cвыключен ");
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPlayedBefore()) {
            return;
        }

        for (String line : message) {
            line = line.replace("{player}", player.getName());
            player.sendMessage(HexUtility.translate(line));
        }
        this.saveDefaultConfig();
        License license = new License(this.getConfig().getString("Security_Key"), "ссылка на ваш сайт лицензии", this);

        license.request();

        if (license.isValid()) {

            Bukkit.getConsoleSender().sendMessage("§aLicense valid, enabling plugin...");

        } else {

            Bukkit.getConsoleSender().sendMessage("§cLicense not valid, disabling plugin...");

            Bukkit.getPluginManager().disablePlugin(this);

        }
    }
}
