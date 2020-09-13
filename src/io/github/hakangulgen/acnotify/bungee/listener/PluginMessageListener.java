package io.github.hakangulgen.acnotify.bungee.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import io.github.hakangulgen.acnotify.shared.StaffManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageListener implements Listener {

    private final StaffManager staffManager;

    public PluginMessageListener(StaffManager staffManager) { this.staffManager = staffManager; }

    @EventHandler
    public void onMessage(final PluginMessageEvent event) {
        if (event.isCancelled()) return;

        if (!event.getTag().equalsIgnoreCase("acnotify:channel")) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());

        final String subChannel = in.readUTF();

        if (!subChannel.equals("notification")) return;

        final String notification = in.readUTF();

        for (final String staffName : staffManager.getAllStaff()) {
            final ProxiedPlayer staff = ProxyServer.getInstance().getPlayer(staffName);
            if (staff != null)
                staff.sendMessage(TextComponent.fromLegacyText(notification));
        }
    }
}
