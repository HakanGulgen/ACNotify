package io.github.hakangulgen.acnotify.bukkit.util;

import io.github.hakangulgen.acnotify.bukkit.ACNotifyPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigurationUtil {

    private final ACNotifyPlugin plugin;

    public ConfigurationUtil(ACNotifyPlugin plugin) { this.plugin = plugin; }

    public YamlConfiguration getConfiguration(String filePath) {
        final File dataFolder = plugin.getDataFolder();
        final File file = new File(filePath.replace("%datafolder%", dataFolder.toPath().toString()));

        if (file.exists())
            return YamlConfiguration.loadConfiguration(file);
        else return new YamlConfiguration();
    }

    public void createConfiguration(String file) {
        try {
            final File dataFolder = plugin.getDataFolder();

            file = file.replace("%datafolder%", dataFolder.toPath().toString());

            final File configFile = new File(file);

            if (!configFile.exists()) {
                final String[] files = file.split("/");
                final InputStream inputStream = plugin.getClass().getClassLoader().getResourceAsStream(files[files.length - 1]);
                final File parentFile = configFile.getParentFile();

                if (parentFile != null) parentFile.mkdirs();

                if (inputStream != null) {
                    Files.copy(inputStream, configFile.toPath());
                } else configFile.createNewFile();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
