package io.github.hakangulgen.acnotify.bukkit.listener;

import io.github.hakangulgen.acnotify.shared.StaffManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final StaffManager staffManager;

    public PlayerQuitListener(StaffManager staffManager) { this.staffManager = staffManager; }

    @EventHandler
    public void onJoin(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        staffManager.removeStaff(player.getName());
    }
}
