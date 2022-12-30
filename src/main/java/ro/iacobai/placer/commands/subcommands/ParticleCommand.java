package ro.iacobai.placer.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.placer.commands.SubCommand;
import ro.iacobai.placer.data.DataHandler;
import ro.iacobai.placer.tasks.Particles;

public class ParticleCommand extends SubCommand {
    DataHandler dataHandler = new DataHandler();
    @Override
    public String getName() {
        return "particle";
    }

    @Override
    public String getDescription() {
        return "Highlight the selected placer area for 120 seconds";
    }

    @Override
    public String getSyntax() {
        return "/placer particle";
    }

    @Override
    public void perform(Player player, String[] args) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(DataHandler.get_bool(dataHandler.namespaceKey_Highlight,data)==0){
            Particles particles = new Particles();
            particles.run_t(player);
            DataHandler.change_bool(dataHandler.namespaceKey_Highlight,data,player,null);

        }
        else {
            player.sendMessage(ChatColor.DARK_RED+"---------------------");
            player.sendMessage(ChatColor.RED+"The particle highlight has stopped!");
            player.sendMessage(ChatColor.DARK_RED+"---------------------");
            Bukkit.getScheduler().cancelTask(DataHandler.get_int(dataHandler.namespaceKey_Task_Particle_Id,data));
            DataHandler.change_bool(dataHandler.namespaceKey_Highlight,data,player,null);
        }

    }
}
