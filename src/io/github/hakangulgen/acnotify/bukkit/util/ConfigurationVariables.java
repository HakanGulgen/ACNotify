package io.github.hakangulgen.acnotify.bukkit.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;

public class ConfigurationVariables {

    private final ConfigurationUtil configurationUtil;

    private String prefix, onlyConsole, noPermission, reloaded, serverName, autoNotifyFormat;

    private boolean bungeecord, autoNotifyEnabled, notifyPrefix;

    private int minViolation;

    public ConfigurationVariables(ConfigurationUtil configurationUtil) {
        this.configurationUtil = configurationUtil;

        reloadConfig();
    }

    public void reloadConfig() {
        final Configuration config = configurationUtil.getConfiguration("%datafolder%/config.yml");

        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("MESSAGES.PREFIX"));
        onlyConsole = ChatColor.translateAlternateColorCodes('&', config.getString("MESSAGES.ONLY_CONSOLE").replace("%prefix%", prefix));
        autoNotifyFormat = ChatColor.translateAlternateColorCodes('&', config.getString("AUTO_NOTIFICATION.FORMAT"));
        noPermission = ChatColor.translateAlternateColorCodes('&', config.getString("MESSAGES.NO_PERMISSION").replace("%prefix%", prefix));
        reloaded = ChatColor.translateAlternateColorCodes('&', config.getString("MESSAGES.RELOADED").replace("%prefix%", prefix));
        serverName = config.getString("BUNGEECORD.SERVER_NAME");
        bungeecord = config.getBoolean("BUNGEECORD.ENABLED");
        notifyPrefix = config.getBoolean("PREFIX_FOR_NOTIFICATIONS");
        autoNotifyEnabled = config.getBoolean("AUTO_NOTIFICATION.ENABLED");
        minViolation = config.getInt("AUTO_NOTIFICATION.MIN_VIOLATION");
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
