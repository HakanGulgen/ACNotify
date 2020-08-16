package io.github.hakangulgen.acnotify.bungee.listener;

import io.github.hakangulgen.acnotify.shared.StaffManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import org.bukkit.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class PluginMessageListener implements Listener {

    private final StaffManager staffManager;

    public PluginMessageListener(StaffManager staffManager) { this.staffManager = staffManager; }

    @EventHandler
    public void onMessage(PluginMessageEvent e) {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
        try {
            if (e.getTag().equals("ANTICHEATNOTIFY")) {
                String utf = in.readUTF();
                if (utf.equalsIgnoreCase("acnotify")) {
                    String message = in.readUTF();
                    for (final String staffName : staffManager.getAllStaff()) {
                        ProxiedPlayer staff = ProxyServer.getInstance().getPlayer(staffName);
                        if (staff != null)
                            staff.sendMessage(TextComponent.fromLegacyText(message));
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
