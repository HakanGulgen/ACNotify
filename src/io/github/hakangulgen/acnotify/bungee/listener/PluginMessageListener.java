package io.github.hakangulgen.acnotify.bungee.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import io.github.hakangulgen.acnotify.shared.StaffManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Objects;

public class PluginMessageListener implements Listener {

    private final StaffManager staffManager;

    public PluginMessageListener(StaffManager staffManager) { this.staffManager = staffManager; }

    @EventHandler(priority = 32)
    public void onMessage(final PluginMessageEvent event) {
        if (event.isCancelled()) return;

        if (!event.getTag().equalsIgnoreCase("acnotify:channel")) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());

        final String notification = in.readUTF();

        staffManager.getAllStaff().stream().map(staffName -> ProxyServer.getInstance().getPlayer(staffName)).filter(Objects::nonNull).forEach(staff -> staff.sendMessage(TextComponent.fromLegacyText(notification)));
    }
}
