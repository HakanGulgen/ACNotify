package io.github.hakangulgen.acnotify.bukkit.listener;

import io.github.hakangulgen.acnotify.bukkit.ACNotifyPlugin;
import io.github.hakangulgen.acnotify.bukkit.util.ConfigurationVariables;
import io.github.hakangulgen.acnotify.bukkit.util.Utilities;
import io.github.hakangulgen.acnotify.shared.StaffManager;
import me.vagdedes.spartan.api.API;
import me.vagdedes.spartan.api.PlayerViolationEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class SpartanViolationListener implements Listener {

    private final ACNotifyPlugin plugin;
    private final ConfigurationVariables variables;
    private final StaffManager staffManager;

    public SpartanViolationListener(ACNotifyPlugin plugin, ConfigurationVariables variables, StaffManager staffManager) {
        this.plugin = plugin;
        this.variables = variables;
        this.staffManager = staffManager;
    }

    @EventHandler
    public void onViolationEvent(final PlayerViolationEvent event) {
        if (variables.isAutoNotifyEnabled()) {

            final int vls = event.getViolation();

            if (vls >= variables.getMinViolation()) {

                final Utilities utilities = plugin.getUtilities();

                final Player player = event.getPlayer();
                final int ping = API.getPing(player);
                final String hack = event.getHackType() + "";
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
                    staffManager.getAllStaff().stream().map(staffName -> plugin.getServer().getPlayer(staffName)).filter(Objects::nonNull).forEach(staff -> staff.sendMessage(autoNotifyFormat));
                }
            }
        }
    }
}
