package ro.iacobai.placer.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.placer.commands.SubCommand;
import ro.iacobai.placer.data.DataHandler;

public class PauseCommand extends SubCommand {
    DataHandler dataHandler = new DataHandler();
    @Override
    public String getName() {
        return "pause";
    }

    @Override
    public String getDescription() {
        return "Pause a running placer!";
    }

    @Override
    public String getSyntax() {
        return "/placer pause";
    }

    @Override
    public void perform(Player player, String[] args) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(DataHandler.get_bool(dataHandler.namespaceKey_Pause,data)==0 && DataHandler.get_bool(dataHandler.namespaceKey_Running,data)==1){
            Bukkit.getScheduler().cancelTask(DataHandler.get_int(dataHandler.namespaceKey_Task_Id,data));
            DataHandler.change_bool(dataHandler.namespaceKey_Pause,data,player,null);
            player.sendMessage(ChatColor.DARK_RED+"---------------------");
            player.sendMessage(ChatColor.GREEN+"The placer has been paused!");
            player.sendMessage(ChatColor.DARK_RED+"---------------------");
        }
        else {
            player.sendMessage(ChatColor.DARK_RED+"---------------------");
            player.sendMessage(ChatColor.RED+"Nothing to pause or the placer is already paused!");
            player.sendMessage(ChatColor.DARK_RED+"---------------------");
        }
    }
}
