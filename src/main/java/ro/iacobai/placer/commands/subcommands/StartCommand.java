package ro.iacobai.placer.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.placer.commands.SubCommand;
import ro.iacobai.placer.data.DataHandler;

import static java.lang.Math.abs;
import static ro.iacobai.placer.commands.subcommands.StatusCommand.location_send;

public class StartCommand extends SubCommand {
    DataHandler dataHandler = new DataHandler();
    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Starts the placer, but needs confirmation!";
    }

    @Override
    public String getSyntax() {
        return "/placer start";
    }

    @Override
    public void perform(Player player, String[] args) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        Location pos1 = DataHandler.get_position(dataHandler.namespaceKey_Pos1,data);
        Location pos2 = DataHandler.get_position(dataHandler.namespaceKey_Pos2,data);
        int h = (int) (abs(abs(pos1.getY()) - abs(pos2.getY())))+1;
        int l = (int)(abs(abs(pos1.getX()) - abs(pos2.getX())))+1;
        int w = (int)(abs(abs(pos1.getZ()) - abs(pos2.getZ())))+1;
        double number_of_blocks = h*l*w;
        DataHandler.save_double(dataHandler.namespaceKey_Blocks_Remaining,data,number_of_blocks);
        player.sendMessage(ChatColor.DARK_RED+"---------------------");
        location_send(dataHandler.namespaceKey_Pos1,data,player,"Pos1 is: ");
        location_send(dataHandler.namespaceKey_Pos2,data,player,"Pos2 is: ");
        player.sendMessage("This will place : "+ ChatColor.GREEN+number_of_blocks+" blocks");
        player.sendMessage("Confirm this with /placer confirm!");
        player.sendMessage(ChatColor.DARK_RED+"---------------------");
        DataHandler.change_bool(dataHandler.namespaceKey_Await_Confirm,data,player,null);

    }
}
