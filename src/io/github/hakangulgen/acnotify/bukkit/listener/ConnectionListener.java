package io.github.hakangulgen.acnotify.bukkit.listener;

import io.github.hakangulgen.acnotify.bukkit.util.ConfigurationVariables;
import io.github.hakangulgen.acnotify.shared.StaffManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    private final StaffManager staffManager;
    private final ConfigurationVariables variables;

    public ConnectionListener(StaffManager staffManager, ConfigurationVariables variables) {
        this.staffManager = staffManager;
        this.variables = variables;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(final PlayerJoinEvent event) {
        if (variables.isBungeeModeEnabled()) return;

        final Player player = event.getPlayer();

        if (player.hasPermission("acnotify.see"))
            staffManager.addStaff(player.getName());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(final PlayerQuitEvent event) {
        if (variables.isBungeeModeEnabled()) return;

        final Player player = event.getPlayer();
        final String name = player.getName();

        staffManager.removeStaff(name);
    }
}
