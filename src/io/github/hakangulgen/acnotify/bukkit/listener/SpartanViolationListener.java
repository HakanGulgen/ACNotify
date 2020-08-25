package io.github.hakangulgen.acnotify.bukkit.listener;

import io.github.hakangulgen.acnotify.bukkit.ACNotifyPlugin;
import io.github.hakangulgen.acnotify.bukkit.util.ConfigurationVariables;
import io.github.hakangulgen.acnotify.shared.StaffManager;
import me.vagdedes.spartan.api.API;
import me.vagdedes.spartan.api.PlayerViolationEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SpartanViolationListener implements Listener {

    private final ACNotifyPlugin plugin;
    private final ConfigurationVariables settings;
    private final StaffManager staffManager;

    public SpartanViolationListener(ACNotifyPlugin plugin, ConfigurationVariables settings, StaffManager staffManager) {
        this.plugin = plugin;
        this.settings = settings;
        this.staffManager = staffManager;
    }

    @EventHandler
    public void onViolationEvent(final PlayerViolationEvent event) {
        if (settings.isAutoNotifyEnabled()) {
            final int vls = event.getViolation();

            if (vls >= settings.getMinViolation()) {
                final Player player = event.getPlayer();
                final int ping = API.getPing(player);
                final String hack = event.getHackType() + "";
                final String autoNotifyFormat = settings.getAutoNotifyFormat()
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
