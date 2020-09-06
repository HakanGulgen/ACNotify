package io.github.hakangulgen.acnotify.bukkit;

import io.github.hakangulgen.acnotify.bukkit.command.Notify;
import io.github.hakangulgen.acnotify.bukkit.command.NotifyReload;
import io.github.hakangulgen.acnotify.bukkit.listener.ConnectionListener;
import io.github.hakangulgen.acnotify.bukkit.listener.MatrixViolationListener;
import io.github.hakangulgen.acnotify.bukkit.listener.ReflexViolationListener;
import io.github.hakangulgen.acnotify.bukkit.listener.SpartanViolationListener;
import io.github.hakangulgen.acnotify.bukkit.util.ConfigurationUtil;
import io.github.hakangulgen.acnotify.bukkit.util.ConfigurationVariables;
import io.github.hakangulgen.acnotify.bukkit.util.Utilities;
import io.github.hakangulgen.acnotify.shared.StaffManager;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ACNotifyPlugin extends JavaPlugin {

    private Utilities utilities;

    @Override
    public void onEnable() {
        final Server server = this.getServer();
        final PluginManager pluginManager = server.getPluginManager();

        final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

        configurationUtil.createConfiguration("%datafolder%/config.yml");

        final ConfigurationVariables settings = new ConfigurationVariables(configurationUtil);

        server.getMessenger().registerOutgoingPluginChannel(this, "acnotify:channel");

        getCommand("acreload").setExecutor(new NotifyReload(settings));

        final StaffManager staffManager = new StaffManager();

        pluginManager.registerEvents(new ConnectionListener(staffManager), this);

        utilities = new Utilities(this);

        if (!settings.isAutoNotifyEnabled()) {
            getCommand("acnotify").setExecutor(new Notify(this, settings, staffManager));
        } else {
            final Logger logger = this.getLogger();

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

    public Utilities getUtilities() { return utilities; }

}
