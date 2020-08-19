package io.github.hakangulgen.acnotify.bukkit.command;

import io.github.hakangulgen.acnotify.bukkit.ACNotifyPlugin;
import io.github.hakangulgen.acnotify.bukkit.util.ConfigurationVariables;
import io.github.hakangulgen.acnotify.shared.StaffManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Notify implements CommandExecutor {

    private final ACNotifyPlugin plugin;
    private final ConfigurationVariables settings;
    private final StaffManager staffManager;

    public Notify(ACNotifyPlugin plugin, ConfigurationVariables settings, StaffManager staffManager) {
        this.plugin = plugin;
        this.settings = settings;
        this.staffManager = staffManager;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!settings.isAutoNotifyEnabled()) {
            if (sender instanceof ConsoleCommandSender) {
                if (args.length != 0) {
                    StringBuilder msg = new StringBuilder();
                    for (String arg : args) {
                        msg.append(arg).append(" ");
                    }
                    String notifyMessage = settings.isNotifyPrefix() ? settings.getPrefix() + " " + msg : msg + "";
                    if (settings.isBungeeModeEnabled()) {
                        Player randomPlayer = plugin.getRandomPlayer();
                        if (randomPlayer != null) {
                            ByteArrayOutputStream b = new ByteArrayOutputStream();
                            DataOutputStream out = new DataOutputStream(b);
                            try {
                                out.writeUTF("notification");
                                out.writeUTF(ChatColor.translateAlternateColorCodes('&', notifyMessage.replace("%server%", settings.getServerName())));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            randomPlayer.sendPluginMessage(plugin, "acnotify:channel", b.toByteArray());
                        }
                    } else {
                        for (final String staffName : staffManager.getAllStaff()) {
                            final Player staff = plugin.getServer().getPlayer(staffName);
                            if (staff != null)
                                staff.sendMessage(ChatColor.translateAlternateColorCodes('&', notifyMessage.replace("%server%", settings.getServerName())));
                        }
                    }
                }
            } else {
                sender.sendMessage(settings.getOnlyConsole());
            }
        }
        return false;
    }
}
