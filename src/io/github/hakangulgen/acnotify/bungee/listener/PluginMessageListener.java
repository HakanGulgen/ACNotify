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
    public void onMessage(final PluginMessageEvent event) {
        if (!event.isCancelled() && event.getTag().equals("acnotify:channel")) {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));
            try {
                final String subChannel = in.readUTF();
                if (subChannel.equals("notification")) {
                    final String notification = in.readUTF();
                    for (final String staffName : staffManager.getAllStaff()) {
                        final ProxiedPlayer staff = ProxyServer.getInstance().getPlayer(staffName);
                        if (staff != null)
                            staff.sendMessage(TextComponent.fromLegacyText(notification));
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
