package io.github.hakangulgen.acnotify.bukkit.listener;

import io.github.hakangulgen.acnotify.bukkit.ACNotifyPlugin;
import io.github.hakangulgen.acnotify.bukkit.util.Settings;
import io.github.hakangulgen.acnotify.shared.StaffManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import rip.reflex.api.ReflexAPI;
import rip.reflex.api.ReflexAPIProvider;
import rip.reflex.api.event.ReflexCheckEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ReflexViolationListener implements Listener {

    private final ACNotifyPlugin plugin;
    private final Settings settings;
    private final StaffManager staffManager;

    private final ReflexAPI reflexAPI;

    public ReflexViolationListener(ACNotifyPlugin plugin, Settings settings, StaffManager staffManager) {
        this.plugin = plugin;
        this.settings = settings;
        this.staffManager = staffManager;
        this.reflexAPI = ReflexAPIProvider.getAPI();
    }

    @EventHandler
    public void onViolationEvent(ReflexCheckEvent event) {
        if (settings.isAutoNotifyEnabled()) {
            Player player = event.getPlayer();
            String vls = event.getViolationId();
            int ping = reflexAPI.getPing(player);
            String hack = event.getCheat() + "";
            String autoNotifyFormat = settings.getAutoNotifyFormat()
                    .replace("&", "§")
                    .replace("%prefix%" , settings.getPrefix())
                    .replace("%ping%", ping + "")
                    .replace("%player%", player.getName())
                    .replace("%hack%", hack)
                    .replace("%server%", settings.getServerName())
                    .replace("%vls%", vls + "");
            if (settings.isBungeeModeEnabled()) {
                Player randomPlayer = plugin.getRandomPlayer();
                if (randomPlayer != null) {
                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(b);
                    try {
                        out.writeUTF(autoNotifyFormat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    randomPlayer.sendPluginMessage(plugin, "acnotify:notify", b.toByteArray());
                }
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
