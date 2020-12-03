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

    @Override
    public void onEnable() {
        final Server server = this.getServer();
        final PluginManager pluginManager = server.getPluginManager();

        final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

        configurationUtil.createConfiguration("%datafolder%/config.yml");

        final ConfigurationVariables variables = new ConfigurationVariables(configurationUtil);

        server.getMessenger().registerOutgoingPluginChannel(this, "acnotify:channel");

        getCommand("acreload").setExecutor(new NotifyReload(variables));

        final StaffManager staffManager = new StaffManager();

        pluginManager.registerEvents(new ConnectionListener(staffManager, variables), this);

        Utilities utilities = new Utilities(this);

        if (!variables.isAutoNotifyEnabled()) {
            getCommand("acnotify").setExecutor(new Notify(this, variables, staffManager, utilities));
        } else {
            final Logger logger = this.getLogger();

            if (pluginManager.getPlugin("Spartan") != null) {
                pluginManager.registerEvents(new SpartanViolationListener(this, variables, staffManager, utilities), this);

                logger.info("AUTO-NOTIFICATION hooked with Spartan.");
            } else if (pluginManager.getPlugin("Matrix") != null) {
                pluginManager.registerEvents(new MatrixViolationListener(this, variables, staffManager, utilities), this);

                logger.info("AUTO-NOTIFICATION hooked with Matrix.");
            } else if (pluginManager.getPlugin("Reflex") != null) {
                pluginManager.registerEvents(new ReflexViolationListener(this, variables, staffManager, utilities), this);

                logger.info("AUTO-NOTIFICATION hooked with Reflex.");
            } else {
                variables.setAutoNotifyEnabled(false);

                getCommand("acnotify").setExecutor(new Notify(this, variables, staffManager, utilities));

                logger.info("There is no supported anti cheat plugin for AUTO-NOTIFICATION.");
                logger.info("Supported plugins: Spartan, Reflex, Matrix.");
                logger.info("AUTO-NOTIFICATION has been disabled.");
            }
        }
    }
}
