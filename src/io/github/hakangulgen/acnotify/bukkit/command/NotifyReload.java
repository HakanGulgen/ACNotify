package io.github.hakangulgen.acnotify.bukkit.command;

import io.github.hakangulgen.acnotify.bukkit.util.ConfigurationVariables;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NotifyReload implements CommandExecutor {

    private final ConfigurationVariables settings;

    public NotifyReload(ConfigurationVariables settings) {
        this.settings = settings;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (!player.hasPermission("acnotify.reload")) {
                player.sendMessage(settings.getNoPermission());
                return true;
            }
        }

        settings.reloadConfig();

        sender.sendMessage(settings.getReloaded());

        return false;
    }
}
