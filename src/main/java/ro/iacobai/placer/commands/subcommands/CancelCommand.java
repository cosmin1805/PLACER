package ro.iacobai.placer.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.placer.commands.SubCommand;
import ro.iacobai.placer.data.DataHandler;

public class CancelCommand extends SubCommand {
    DataHandler dataHandler = new DataHandler();
    @Override
    public String getName() {
        return "cancel";
    }

    @Override
    public String getDescription() {
        return "Cancel some event";
    }

    @Override
    public String getSyntax() {
        return "/placer cancel";
    }

    @Override
    public void perform(Player player, String[] args) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(DataHandler.get_bool(dataHandler.namespaceKey_Await_Confirm,data) == 1){
            DataHandler.change_bool(dataHandler.namespaceKey_Await_Confirm,data,player,null);
            player.sendMessage(ChatColor.GREEN+"You canceled your /placer start or /placer cancel !");
        }
        else if (DataHandler.get_bool(dataHandler.namespaceKey_Running,data)==1){
            DataHandler.change_bool(dataHandler.namespaceKey_Await_Confirm,data,player,null);
            player.sendMessage(ChatColor.AQUA+"---------------------");
            player.sendMessage("Confirm this with /placer confirm or cancel it with /placer cancel !");
            player.sendMessage(ChatColor.AQUA+"---------------------");
        }
        else {
            player.sendMessage(ChatColor.RED+"Nothing to cancel !");
        }
    }
}
