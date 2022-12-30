package ro.iacobai.placer.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.placer.commands.SubCommand;
import ro.iacobai.placer.data.DataHandler;

public class SelectCommand extends SubCommand {
    DataHandler dataHandler = new DataHandler();
    @Override
    public String getName() {
        return "select";
    }

    @Override
    public String getDescription() {
        return "Scan with a stick the positions for placer.";
    }

    @Override
    public String getSyntax() {
        return "/placer select <pos/chest>";
    }

    @Override
    public void perform(Player player, String[] args) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        player.sendMessage(ChatColor.DARK_RED+"---------------------");
        DataHandler.change_bool(dataHandler.namespacesKey_Pos_Select,data,player,"Placer position selector ");
        player.sendMessage(ChatColor.DARK_RED+"---------------------");
    }
}
