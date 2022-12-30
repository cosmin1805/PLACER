package ro.iacobai.placer.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.placer.data.DataHandler;
import ro.iacobai.placer.tasks.PlaceBlocks;

public class OnJoin implements Listener {
    DataHandler dataHandler = new DataHandler();
    @EventHandler
    public void onjoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(DataHandler.get_bool(dataHandler.namespaceKey_Running,data)==1 && DataHandler.get_bool(dataHandler.namespaceKey_Pause,data)==0){
            PlaceBlocks placeBlocks = new PlaceBlocks();
            placeBlocks.run_t(player);
            System.out.println("Task placer of " +player.getName()+" resumed!");
            player.sendMessage(ChatColor.GREEN+"Your Placer Task was resumed!");
        }
    }
}
