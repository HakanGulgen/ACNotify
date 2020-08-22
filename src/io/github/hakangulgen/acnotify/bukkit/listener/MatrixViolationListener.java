package io.github.hakangulgen.acnotify.bukkit.listener;

import io.github.hakangulgen.acnotify.bukkit.ACNotifyPlugin;
import io.github.hakangulgen.acnotify.bukkit.util.ConfigurationVariables;
import io.github.hakangulgen.acnotify.shared.StaffManager;
import me.rerere.matrix.api.MatrixAPI;
import me.rerere.matrix.api.MatrixAPIProvider;
import me.rerere.matrix.api.events.PlayerViolationEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MatrixViolationListener implements Listener {

    private final ACNotifyPlugin plugin;
    private final ConfigurationVariables settings;
    private final StaffManager staffManager;

    private final MatrixAPI matrixAPI;

    public MatrixViolationListener(ACNotifyPlugin plugin, ConfigurationVariables settings, StaffManager staffManager) {
        this.plugin = plugin;
        this.settings = settings;
        this.staffManager = staffManager;
        this.matrixAPI = MatrixAPIProvider.getAPI();
    }

    @EventHandler
    public void onViolationEvent(PlayerViolationEvent event) {
        if (settings.isAutoNotifyEnabled()) {
            Player player = event.getPlayer();
            int vls = event.getViolations();
            if (vls >= settings.getMinViolation()) {
                int ping = matrixAPI.getLatency(player);
                String hack = event.getHackType() + "";
                String autoNotifyFormat = settings.getAutoNotifyFormat()
                        .replace("&", "§")
                        .replace("%prefix%" , settings.getPrefix())
                        .replace("%ping%", ping + "")
                        .replace("%player%", player.getName())
                        .replace("%hack%", hack)
                        .replace("%server%", settings.getServerName())
                        .replace("%vls%", vls + "");
                if (settings.isBungeeModeEnabled()) {
                    plugin.sendPluginMessage(autoNotifyFormat);
                } else {
                    for (final String staffName : staffManager.getAllStaff()) {
                        final Player staff = plugin.getServer().getPlayer(staffName);
                        if (staff != null)
                            staff.sendMessage(autoNotifyFormat);
                    }
                }
            }
        }
    }
}
