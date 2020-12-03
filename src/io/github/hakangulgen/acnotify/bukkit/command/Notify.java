package io.github.hakangulgen.acnotify.bukkit.command;

import io.github.hakangulgen.acnotify.bukkit.ACNotifyPlugin;
import io.github.hakangulgen.acnotify.bukkit.util.ConfigurationVariables;
import io.github.hakangulgen.acnotify.bukkit.util.Utilities;
import io.github.hakangulgen.acnotify.shared.StaffManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.Objects;

public class Notify implements CommandExecutor {

    private final ACNotifyPlugin plugin;
    private final ConfigurationVariables variables;
    private final StaffManager staffManager;
    private final Utilities utilities;

    public Notify(ACNotifyPlugin plugin, ConfigurationVariables variables, StaffManager staffManager, Utilities utilities) {
        this.plugin = plugin;
        this.variables = variables;
        this.staffManager = staffManager;
        this.utilities = utilities;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!variables.isAutoNotifyEnabled()) {
            if (sender instanceof ConsoleCommandSender) {
                if (args.length != 0) {

                    StringBuilder msg = new StringBuilder();

                    for (String arg : args) {
                        msg.append(arg).append(" ");
                    }

                    final String notifyMessage = variables.isNotifyPrefix() ? variables.getPrefix() + " " + msg : msg + "";

                    if (variables.isBungeeModeEnabled()) {
                        utilities.sendPluginMessage(notifyMessage.replace("&", "§").replace("%server%", variables.getServerName()));
                    } else {
                        staffManager.getAllStaff().stream().map(staffName -> plugin.getServer().getPlayer(staffName)).filter(Objects::nonNull).forEach(staff -> staff.sendMessage(ChatColor.translateAlternateColorCodes('&', notifyMessage.replace("%server%", variables.getServerName()))));
                    }
                }
            } else {
                sender.sendMessage(variables.getOnlyConsole());
            }
        }
        return false;
    }
}
