package io.github.hakangulgen.acnotify.bukkit.listener;

import io.github.hakangulgen.acnotify.shared.StaffManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final StaffManager staffManager;

    public PlayerJoinListener(StaffManager staffManager) { this.staffManager = staffManager; }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if (player.hasPermission("acnotify.see"))
            staffManager.addStaff(player.getName());
    }
}
