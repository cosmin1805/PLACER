package ro.iacobai.placer.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.placer.commands.SubCommand;
import ro.iacobai.placer.data.DataHandler;
import ro.iacobai.placer.tasks.PlaceBlocks;

public class ResumeCommand extends SubCommand {
    DataHandler dataHandler = new DataHandler();
    PlaceBlocks placeBlocks = new PlaceBlocks();
    @Override
    public String getName() {
        return "resume";
    }

    @Override
    public String getDescription() {
        return "Resume if your placer is on pause!";
    }

    @Override
    public String getSyntax() {
        return "/placer resume";
    }

    @Override
    public void perform(Player player, String[] args) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(DataHandler.get_bool(dataHandler.namespaceKey_Pause,data)==1 && DataHandler.get_bool(dataHandler.namespaceKey_Running,data)==1){
            DataHandler.change_bool(dataHandler.namespaceKey_Pause,data,player,null);
            placeBlocks.run_t(player,DataHandler.get_int(dataHandler.namespaceKey_Task_Next_Time,data));
            player.sendMessage(ChatColor.DARK_RED+"---------------------");
            player.sendMessage(ChatColor.GREEN+"Placer resumed!");
            player.sendMessage(ChatColor.DARK_RED+"---------------------");
        }
        else {
            player.sendMessage(ChatColor.DARK_RED+"---------------------");
            player.sendMessage(ChatColor.RED+"Nothing to resume!");
            player.sendMessage(ChatColor.DARK_RED+"---------------------");
        }
    }
}
