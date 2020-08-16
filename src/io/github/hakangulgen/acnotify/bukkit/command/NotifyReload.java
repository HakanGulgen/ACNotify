package io.github.hakangulgen.acnotify.bukkit.command;

import io.github.hakangulgen.acnotify.bukkit.util.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NotifyReload implements CommandExecutor {

    private final Settings settings;

    public NotifyReload(Settings settings) {
        this.settings = settings;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
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
