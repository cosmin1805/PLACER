package ro.iacobai.placer;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;
import ro.iacobai.placer.commands.CommandManager;
import ro.iacobai.placer.commands.TabComplete;
import ro.iacobai.placer.data.DataHandler;
import ro.iacobai.placer.items.ItemManager;
import ro.iacobai.placer.player.OnJoin;
import ro.iacobai.placer.player.OnLeave;
import ro.iacobai.placer.tasks.Particles;
import ro.iacobai.placer.tasks.PlaceBlocks;

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
        for(Player player : Bukkit.getOnlinePlayers()){
            DataHandler dataHandler = new DataHandler();
            PersistentDataContainer data = player.getPersistentDataContainer();
            if(DataHandler.get_bool(dataHandler.namespaceKey_Running,data)==1 && DataHandler.get_bool(dataHandler.namespaceKey_Pause,data)==0){
                PlaceBlocks placeBlocks = new PlaceBlocks();
                placeBlocks.run_t(player);
                System.out.println("Task of " +player.getName()+" resumed!");
                player.sendMessage(ChatColor.GREEN+"Your Placer Task was resumed!");
            }
            if(DataHandler.get_bool(dataHandler.namespaceKey_Highlight,data)==1){
                Particles particles = new Particles();
                particles.run_t(player);
                System.out.println("Highlight of " +player.getName()+" resumed!");
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
