package xyz.cowgl.moojoincmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MooJoinCMD extends JavaPlugin implements Listener, TabCompleter {

    private File configFile;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        // Register events and tab completer
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("rmoojoincmd")).setExecutor(this);
        Objects.requireNonNull(getCommand("rmoojoincmd")).setTabCompleter(this);

        // Create config file
        configFile = new File(getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);

        // Check if config exists, if not create default
        if (!config.contains("joinCommand")) {
            config.set("joinCommand", "say Welcome %player%");
            saveConfig();
        }

        reloadConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rmoojoincmd")) {
            if (!sender.hasPermission("moojoincmd.reload")) {
                sender.sendMessage("You don't have permission to reload the config!");
                return true;
            }
            reloadConfig();
            sender.sendMessage("MooJoinCMD config reloaded!");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        return new ArrayList<>(); // No tab completion for reload command
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
        saveConfig();
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String command = Objects.requireNonNull(config.getString("joinCommand")).replace("%player%", player.getName());
        player.performCommand(command);
    }
}
