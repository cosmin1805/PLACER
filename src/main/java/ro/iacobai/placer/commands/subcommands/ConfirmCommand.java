package ro.iacobai.placer.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.placer.commands.SubCommand;
import ro.iacobai.placer.data.DataHandler;
import ro.iacobai.placer.tasks.PlaceBlocks;

public class ConfirmCommand extends SubCommand {
    DataHandler dataHandler = new DataHandler();
    @Override
    public String getName() {
        return "confirm";
    }

    @Override
    public String getDescription() {
        return "Confirm a placer action";
    }

    @Override
    public String getSyntax() {
        return "/placer confirm";
    }

    @Override
    public void perform(Player player, String[] args) {
        //
        PersistentDataContainer data = player.getPersistentDataContainer();
        Location pos1 = DataHandler.get_position(dataHandler.namespaceKey_Pos1,data);
        //
        if(DataHandler.get_bool(dataHandler.namespaceKey_Await_Confirm,data) == 1){
            if(DataHandler.get_bool(dataHandler.namespaceKey_Running,data) == 1){
                Bukkit.getScheduler().cancelTask(DataHandler.get_int(dataHandler.namespaceKey_Task_Id,data));
                DataHandler.change_bool(dataHandler.namespaceKey_Await_Confirm,data,player,null);
                DataHandler.change_bool(dataHandler.namespaceKey_Running,data,player,null);
                player.sendMessage(ChatColor.GREEN+"PLACER HAS BEEN CANCELED!");
                if(DataHandler.get_bool(dataHandler.namespaceKey_Pause,data)==1) {
                    DataHandler.change_bool(dataHandler.namespaceKey_Pause,data,player,null);
                }
                return;
            }
            //start
            player.sendMessage(ChatColor.GREEN+"PLACER HAS STARTED!");
            PlaceBlocks placeBlocks = new PlaceBlocks();
            DataHandler.change_bool(dataHandler.namespaceKey_Running,data,player,null);
            DataHandler.save_position(dataHandler.namespacesKey_PosCurrent,data,pos1);
            placeBlocks.run_t(player);
            DataHandler.change_bool(dataHandler.namespaceKey_Await_Confirm,data,player,null);
        }else {
            player.sendMessage(ChatColor.RED+"First do /placer start or /placer stop !");
        }
    }
}
