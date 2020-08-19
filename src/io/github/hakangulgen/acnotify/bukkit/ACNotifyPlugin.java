package io.github.hakangulgen.acnotify.bukkit;

import io.github.hakangulgen.acnotify.bukkit.command.Notify;
import io.github.hakangulgen.acnotify.bukkit.command.NotifyReload;
import io.github.hakangulgen.acnotify.bukkit.listener.*;
import io.github.hakangulgen.acnotify.bukkit.util.ConfigurationUtil;
import io.github.hakangulgen.acnotify.bukkit.util.Settings;
import io.github.hakangulgen.acnotify.shared.StaffManager;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.logging.Logger;

public class ACNotifyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        final Server server = this.getServer();
        final PluginManager pluginManager = server.getPluginManager();
        final Logger logger = this.getLogger();

        final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

        configurationUtil.createConfiguration("%datafolder%/config.yml");

        final Settings settings = new Settings(configurationUtil);

        server.getMessenger().registerOutgoingPluginChannel(this, "acnotify:notify");

        getCommand("acreload").setExecutor(new NotifyReload(settings));

        StaffManager staffManager = new StaffManager();

        pluginManager.registerEvents(new PlayerJoinListener(staffManager), this);
        pluginManager.registerEvents(new PlayerQuitListener(staffManager), this);

        if (!settings.isAutoNotifyEnabled()) {
            getCommand("acnotify").setExecutor(new Notify(this, settings, staffManager));
        } else {
            if (pluginManager.getPlugin("Spartan") != null) {
                pluginManager.registerEvents(new SpartanViolationListener(this, settings, staffManager), this);
                logger.info("AUTO-NOTIFY hooked with Spartan.");
            } else if (pluginManager.getPlugin("Matrix") != null) {
                pluginManager.registerEvents(new MatrixViolationListener(this, settings, staffManager), this);
                logger.info("AUTO-NOTIFY hooked with Matrix.");
            } else if (pluginManager.getPlugin("Reflex") != null) {
                pluginManager.registerEvents(new ReflexViolationListener(this, settings, staffManager), this);
                logger.info("AUTO-NOTIFY hooked with Reflex.");
            } else {
                settings.setAutoNotifyEnabled(false);

                getCommand("acnotify").setExecutor(new Notify(this, settings, staffManager));

                logger.info("There is no supported anti cheat plugin for AUTO-NOTIFY.");
                logger.info("Supported plugins: Spartan, Reflex, Matrix.");
                logger.info("AUTO-NOTIFY has been disabled.");
            }
        }
    }

    public Player getRandomPlayer() {
        Collection<? extends Player> players = getServer().getOnlinePlayers();
        if (!players.isEmpty()) {
            int i = (int) (players.size() * Math.random());
            return players.toArray(new Player[0])[i];
        }
        return null;
    }
}
