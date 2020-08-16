package io.github.hakangulgen.acnotify.bungee.listener;

import io.github.hakangulgen.acnotify.shared.StaffManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLoginListener implements Listener {

    private final StaffManager staffManager;

    public PostLoginListener(StaffManager staffManager) { this.staffManager = staffManager; }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        final ProxiedPlayer player = event.getPlayer();
        if (player.hasPermission("acnotify.see"))
            staffManager.addStaff(player.getName());
    }
}
