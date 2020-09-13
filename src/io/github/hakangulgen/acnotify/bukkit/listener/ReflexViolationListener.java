package io.github.hakangulgen.acnotify.bukkit.listener;

import io.github.hakangulgen.acnotify.bukkit.ACNotifyPlugin;
import io.github.hakangulgen.acnotify.bukkit.util.ConfigurationVariables;
import io.github.hakangulgen.acnotify.bukkit.util.Utilities;
import io.github.hakangulgen.acnotify.shared.StaffManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import rip.reflex.api.event.ReflexCheckEvent;

public class ReflexViolationListener implements Listener {

    private final ACNotifyPlugin plugin;
    private final ConfigurationVariables variables;
    private final StaffManager staffManager;

    public ReflexViolationListener(ACNotifyPlugin plugin, ConfigurationVariables variables, StaffManager staffManager) {
        this.plugin = plugin;
        this.variables = variables;
        this.staffManager = staffManager;
    }

    @EventHandler
    public void onViolationEvent(final ReflexCheckEvent event) {
        if (variables.isAutoNotifyEnabled()) {

            final Utilities utilities = plugin.getUtilities();

            final Player player = event.getPlayer();
            final String vls = event.getViolationId();
            final int ping = utilities.getPing(player);
            final String hack = event.getCheat() + "";
            final String autoNotifyFormat = variables.getAutoNotifyFormat()
                    .replace("&", "§")
                    .replace("%prefix%" , variables.getPrefix())
                    .replace("%ping%", ping + "")
                    .replace("%player%", player.getName())
                    .replace("%hack%", hack)
                    .replace("%server%", variables.getServerName())
                    .replace("%vls%", vls + "");

            if (variables.isBungeeModeEnabled()) {
                utilities.sendPluginMessage(autoNotifyFormat);
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
