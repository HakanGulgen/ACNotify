package io.github.hakangulgen.acnotify.bukkit.command;

import io.github.hakangulgen.acnotify.bukkit.util.ConfigurationVariables;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NotifyReload implements CommandExecutor {

    private final ConfigurationVariables variables;

    public NotifyReload(ConfigurationVariables variables) { this.variables = variables; }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (!player.hasPermission("acnotify.reload")) {
                player.sendMessage(variables.getNoPermission());
                return true;
            }
        }

        variables.reloadConfig();

        sender.sendMessage(variables.getReloaded());

        return false;
    }
}
