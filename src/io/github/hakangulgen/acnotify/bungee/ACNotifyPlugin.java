package io.github.hakangulgen.acnotify.bungee;

import io.github.hakangulgen.acnotify.bungee.listener.PlayerDisconnectListener;
import io.github.hakangulgen.acnotify.bungee.listener.PluginMessageListener;
import io.github.hakangulgen.acnotify.bungee.listener.PostLoginListener;
import io.github.hakangulgen.acnotify.shared.StaffManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class ACNotifyPlugin extends Plugin {

    @Override
    public void onEnable() {
        final PluginManager pluginManager = this.getProxy().getPluginManager();

        StaffManager staffManager = new StaffManager();

        pluginManager.registerListener(this, new PluginMessageListener(staffManager));
        pluginManager.registerListener(this, new PostLoginListener(staffManager));
        pluginManager.registerListener(this, new PlayerDisconnectListener(staffManager));
    }
}
