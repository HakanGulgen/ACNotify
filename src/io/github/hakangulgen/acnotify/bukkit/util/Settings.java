package io.github.hakangulgen.acnotify.bukkit.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;

public class Settings {

    private final ConfigurationUtil configurationUtil;

    private String prefix, onlyConsole, noPermission, reloaded, serverName, autoNotifyFormat;

    private boolean bungeecord, autoNotifyEnabled, notifyPrefix;

    private int minViolation;

    public Settings(ConfigurationUtil configurationUtil) {
        this.configurationUtil = configurationUtil;

        reloadConfig();
    }

    public void reloadConfig() {
        final Configuration config = configurationUtil.getConfiguration("%datafolder%/config.yml");

        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("prefix"));
        onlyConsole = ChatColor.translateAlternateColorCodes('&', config.getString("only-console").replace("%prefix%", prefix));
        autoNotifyFormat = ChatColor.translateAlternateColorCodes('&', config.getString("auto-notify.format"));
        noPermission = ChatColor.translateAlternateColorCodes('&', config.getString("no-permission").replace("%prefix%", prefix));
        reloaded = ChatColor.translateAlternateColorCodes('&', config.getString("reloaded").replace("%prefix%", prefix));
        bungeecord = config.getBoolean("bungeecord");
        notifyPrefix = config.getBoolean("prefixForNotifyMessages");
        serverName = config.getString("server-name");
        autoNotifyEnabled = config.getBoolean("auto-notify.enabled");
        minViolation = config.getInt("auto-notify.min-violation");
    }

    public String getPrefix() { return prefix; }

    public String getOnlyConsole() { return onlyConsole; }

    public String getNoPermission() { return noPermission; }

    public String getReloaded() { return reloaded; }

    public String getServerName() { return serverName; }

    public String getAutoNotifyFormat() { return autoNotifyFormat; }

    public boolean isBungeeModeEnabled() { return bungeecord; }

    public boolean isAutoNotifyEnabled() { return autoNotifyEnabled; }

    public boolean isNotifyPrefix() { return notifyPrefix; }

    public void setAutoNotifyEnabled(boolean autoNotifyEnabled) { this.autoNotifyEnabled = autoNotifyEnabled; }

    public int getMinViolation() { return minViolation; }

}
