package io.github.hakangulgen.acnotify.bungee.listener;

import io.github.hakangulgen.acnotify.shared.StaffManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ConnectionListener implements Listener {

    private final StaffManager staffManager;

    public ConnectionListener(StaffManager staffManager) { this.staffManager = staffManager; }

    @EventHandler(priority = 32)
    public void onPostLogin(final PostLoginEvent event) {
        final ProxiedPlayer player = event.getPlayer();

        if (player.hasPermission("acnotify.see"))
            staffManager.addStaff(player.getName());
    }

    @EventHandler(priority = 32)
    public void onDisconnect(final PlayerDisconnectEvent event) {
        final ProxiedPlayer player = event.getPlayer();
        final String name = player.getName();

        staffManager.removeStaff(name);
    }
}
