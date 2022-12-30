package ro.iacobai.placer;


import org.bukkit.plugin.java.JavaPlugin;
import ro.iacobai.placer.commands.CommandManager;
import ro.iacobai.placer.commands.TabComplete;
import ro.iacobai.placer.items.ItemManager;
import ro.iacobai.placer.player.OnJoin;
import ro.iacobai.placer.player.OnLeave;

public final class PLACER extends JavaPlugin {
    private static  PLACER plugin;
    public  static PLACER getPlugin(){return plugin;}
    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        plugin = this;
        getCommand("placer").setExecutor(new CommandManager());
        getCommand("placer").setTabCompleter(new TabComplete());
        getServer().getPluginManager().registerEvents(new ItemManager(),this);
        getServer().getPluginManager().registerEvents(new OnJoin(),this);
        getServer().getPluginManager().registerEvents(new OnLeave(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
