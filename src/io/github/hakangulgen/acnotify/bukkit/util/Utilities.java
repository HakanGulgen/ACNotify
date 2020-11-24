package io.github.hakangulgen.acnotify.bukkit.util;

import io.github.hakangulgen.acnotify.bukkit.ACNotifyPlugin;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;

public class Utilities {

    private final ACNotifyPlugin plugin;

    public Utilities(ACNotifyPlugin plugin) { this.plugin = plugin; }

    public void sendPluginMessage(final String notification) {
        final Player randomPlayer = this.getRandomPlayer();
        if (randomPlayer != null) {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try {
                out.writeUTF(notification);
            } catch (IOException e) {
                e.printStackTrace();
            }
            randomPlayer.sendPluginMessage(plugin, "acnotify:channel", b.toByteArray());
        }
    }

    private Player getRandomPlayer() {
        Collection<? extends Player> players = plugin.getServer().getOnlinePlayers();
        if (!players.isEmpty()) {
            int i = (int) (players.size() * Math.random());
            return players.toArray(new Player[0])[i];
        }
        return null;
    }
}
