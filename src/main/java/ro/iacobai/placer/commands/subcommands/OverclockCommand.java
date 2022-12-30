package ro.iacobai.placer.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.placer.PLACER;
import ro.iacobai.placer.commands.SubCommand;
import ro.iacobai.placer.data.DataHandler;

public class OverclockCommand extends SubCommand {

    @Override
    public String getName() {
        return "overclock";
    }

    @Override
    public String getDescription() {
        return "overclock for yor placer to be faster";
    }

    @Override
    public String getSyntax() {
        return "/overclock <int>";
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    @Override
    public void perform(Player player, String[] args) {
        DataHandler dataHandler = new DataHandler();
        PLACER placer = PLACER.getPlugin();
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(args.length == 2){
            if(isNumeric(args[1])){
                int time = Integer.parseInt(args[1]);
                if(placer.getConfig().getInt("Time") >= time +2 && time >=0){
                    player.sendMessage(ChatColor.DARK_RED+"---------------------");
                    player.sendMessage(ChatColor.GREEN+"OVERCLOCK SET TO "+ time);
                    player.sendMessage(ChatColor.DARK_RED+"---------------------");
                    DataHandler.save_int(dataHandler.namespaceKey_Overclock,data,time);
                }
                else {
                    player.sendMessage(ChatColor.DARK_RED+"---------------------");
                    player.sendMessage(ChatColor.DARK_RED+"TRY A NUMBER <= THEN: "+ (placer.getConfig().getInt("Time") -2));
                    player.sendMessage(ChatColor.DARK_RED+"---------------------");
                }
                return;
            }
            player.sendMessage(ChatColor.DARK_RED+"---------------------");
            player.sendMessage(ChatColor.DARK_RED+"THIS CAN ONLY BE A NUMBER!");
            player.sendMessage(ChatColor.DARK_RED+"---------------------");
        }
        player.sendMessage(ChatColor.DARK_RED+"---------------------");
        player.sendMessage(ChatColor.DARK_RED+"THIS CAN'T BE EMPTY!");
        player.sendMessage(ChatColor.DARK_RED+"---------------------");

    }
}
