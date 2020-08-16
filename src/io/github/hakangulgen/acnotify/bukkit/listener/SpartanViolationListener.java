package io.github.hakangulgen.acnotify.bukkit.listener;

import com.google.common.collect.Iterables;
import io.github.hakangulgen.acnotify.bukkit.ACNotifyPlugin;
import io.github.hakangulgen.acnotify.bukkit.util.Settings;
import io.github.hakangulgen.acnotify.shared.StaffManager;
import me.vagdedes.spartan.api.API;
import me.vagdedes.spartan.api.PlayerViolationEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SpartanViolationListener implements Listener {

    private final ACNotifyPlugin plugin;
    private final Settings settings;
    private final StaffManager staffManager;

    public SpartanViolationListener(ACNotifyPlugin plugin, Settings settings, StaffManager staffManager) {
        this.plugin = plugin;
        this.settings = settings;
        this.staffManager = staffManager;
    }

    @EventHandler
    public void onViolationEvent(PlayerViolationEvent event) {
        if (settings.isAutoNotifyEnabled()) {
            Player player = event.getPlayer();
            int vls = event.getViolation();
            if (vls > settings.getMinViolation()) {
                int ping = API.getPing(player);
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
                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(b);
                    try {
                        out.writeUTF("acnotify");
                        out.writeUTF(autoNotifyFormat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Iterables.getFirst(Bukkit.getOnlinePlayers(), null).sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
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
