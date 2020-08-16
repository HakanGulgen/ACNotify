package io.github.hakangulgen.acnotify.bukkit.command;

import com.google.common.collect.Iterables;
import io.github.hakangulgen.acnotify.bukkit.ACNotifyPlugin;
import io.github.hakangulgen.acnotify.bukkit.util.Settings;
import io.github.hakangulgen.acnotify.shared.StaffManager;
import org.bukkit.Bukkit;
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
    private final Settings settings;
    private final StaffManager staffManager;

    public Notify(ACNotifyPlugin plugin, Settings settings, StaffManager staffManager) {
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
                        ByteArrayOutputStream b = new ByteArrayOutputStream();
                        DataOutputStream out = new DataOutputStream(b);
                        try {
                            out.writeUTF("acnotify");
                            out.writeUTF(ChatColor.translateAlternateColorCodes('&', notifyMessage));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Iterables.getFirst(Bukkit.getOnlinePlayers(), null).sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
                    } else {
                        for (final String staffName : staffManager.getAllStaff()) {
                            final Player staff = plugin.getServer().getPlayer(staffName);
                            if (staff != null) {
                                staff.sendMessage(ChatColor.translateAlternateColorCodes('&', notifyMessage));
                            }
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
