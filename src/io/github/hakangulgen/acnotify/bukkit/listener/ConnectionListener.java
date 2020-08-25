package io.github.hakangulgen.acnotify.bukkit.listener;

import io.github.hakangulgen.acnotify.shared.StaffManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    private final StaffManager staffManager;

    public ConnectionListener(StaffManager staffManager) { this.staffManager = staffManager; }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        if (player.hasPermission("acnotify.see"))
            staffManager.addStaff(player.getName());
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        staffManager.removeStaff(player.getName());
    }
}
