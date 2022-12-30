package ro.iacobai.placer.tasks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scheduler.BukkitRunnable;
import ro.iacobai.placer.PLACER;
import ro.iacobai.placer.blocks.Chest;
import ro.iacobai.placer.data.DataHandler;

public class PlaceBlocks {
    DataHandler dataHandler = new DataHandler();

    public void run_t(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        Location pos1 = DataHandler.get_position(dataHandler.namespaceKey_Pos1,data);
        Location pos2 = DataHandler.get_position(dataHandler.namespaceKey_Pos2,data);

        Location chest_pos = DataHandler.get_position(dataHandler.namespaceKey_PosChest,data);
        Location hopper_pos = DataHandler.get_position(dataHandler.namespaceKey_PosHopper,data);
        PLACER plugin = PLACER.getPlugin();
        int ID = new BukkitRunnable(){
            @Override
            public void run(){
                Location current_pos = DataHandler.get_position(dataHandler.namespacesKey_PosCurrent,data);
                Block current_block = current_pos.getBlock();
                Material material_current_block = current_block.getBlockData().getMaterial();
                Block hopper = hopper_pos.getBlock();
                Material material_hopper = hopper.getBlockData().getMaterial();
                ItemStack fuel = null;
                if(material_hopper.equals(Material.HOPPER)){
                    Hopper hopper_data = (Hopper) hopper.getState();

                }
                else {
                    DataHandler.change_bool(dataHandler.namespaceKey_Pause,data,player,null);
                    player.sendMessage(ChatColor.RED+"The hopper is missing! So placer was paused!");
                    this.cancel();
                }
                if(new Chest().exists(chest_pos)){
                    if(material_current_block.isAir() || current_block.isLiquid()){
                        Material item = new Chest().use_items(chest_pos);
                        if(item != null){
                            current_block.setType(item);
                        }
                        else {
                            DataHandler.change_bool(dataHandler.namespaceKey_Pause,data,player,null);
                            player.sendMessage(ChatColor.RED+"The chest has no items! So placer was paused!");
                            this.cancel();
                        }
                    }
                }
                else {
                    DataHandler.change_bool(dataHandler.namespaceKey_Pause,data,player,null);
                    player.sendMessage(ChatColor.RED+"The chest is missing! So placer was paused!");
                    this.cancel();
                }
                double blocks = DataHandler.get_double(dataHandler.namespaceKey_Blocks_Remaining,data) - 1;
                DataHandler.save_double(dataHandler.namespaceKey_Blocks_Remaining,data,blocks);
                //STOP IF CURRENT POS IS POS2
                if(current_pos.getX()==pos2.getX() && current_pos.getY()==pos2.getY() && current_pos.getZ()==pos2.getZ()) {
                    DataHandler.change_bool(dataHandler.namespaceKey_Running,data,player,null);
                    player.sendMessage(ChatColor.GREEN+"Placer has finished!");
                    this.cancel();
                }
                //MOKVE THE CURRENT BLOCK
                else if(current_pos.getX()==pos2.getX()  && current_pos.getZ()==pos2.getZ()){
                    current_pos.setX(pos1.getX());
                    if(pos1.getY()>pos2.getY()){
                        current_pos.set(pos1.getX(),current_pos.getY()-1,pos1.getZ());
                    }
                    else{
                        current_pos.set(pos1.getX(),current_pos.getY()+1,pos1.getZ());
                    }
                }
                else if(current_pos.getZ()==pos2.getZ()){

                    if(pos1.getX()>pos2.getX()){
                        current_pos.set(current_pos.getX()-1,current_pos.getY(),pos1.getZ());
                    }
                    else{
                        current_pos.set(current_pos.getX()+1,current_pos.getY(),pos1.getZ());
                    }
                }
                else {
                    if(pos1.getZ()>pos2.getZ()){
                        current_pos.setZ(current_pos.getZ()-1);
                    }
                    else{
                        current_pos.setZ(current_pos.getZ()+1);
                    }
                }
                DataHandler.save_position(dataHandler.namespacesKey_PosCurrent,data,current_pos);
            }
        }.runTaskTimer(plugin, 20,20).getTaskId();
        DataHandler.save_int(dataHandler.namespaceKey_Task_Id,data,ID);
    }
}
