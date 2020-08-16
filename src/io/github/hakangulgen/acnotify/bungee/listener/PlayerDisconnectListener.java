package io.github.hakangulgen.acnotify.bungee.listener;

import io.github.hakangulgen.acnotify.shared.StaffManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnectListener implements Listener {

    private final StaffManager staffManager;

    public PlayerDisconnectListener(StaffManager staffManager) { this.staffManager = staffManager; }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        final ProxiedPlayer player = event.getPlayer();
        staffManager.removeStaff(player.getName());
    }
}
