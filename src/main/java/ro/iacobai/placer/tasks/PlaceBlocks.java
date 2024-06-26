package ro.iacobai.placer.tasks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Hopper;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.type.GlassPane;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scheduler.BukkitRunnable;
import ro.iacobai.placer.PLACER;
import ro.iacobai.placer.blocks.Block_c;
import ro.iacobai.placer.blocks.Chest;
import ro.iacobai.placer.data.DataHandler;

public class PlaceBlocks {
    DataHandler dataHandler = new DataHandler();
    PLACER placer = PLACER.getPlugin();
    public void run_t(Player player,int TIME) {
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
                if(material_hopper.equals(Material.HOPPER)){
                    if(material_current_block.isAir() || current_block.isLiquid()){
                        Hopper hopper_data = (Hopper) hopper.getState();
                        int fuel_add = new ro.iacobai.placer.blocks.Hopper().Fuel_check(hopper_data);
                        int fuel =  DataHandler.get_int(dataHandler.namespaceKey_Fuel,data);
                        if(fuel_add !=0){
                            DataHandler.save_int(dataHandler.namespaceKey_Fuel,data,fuel_add+fuel);
                        }
                        if(fuel + fuel_add !=0 && DataHandler.get_int(dataHandler.namespaceKey_Overclock,data) + 1 <= fuel+fuel_add){
                            DataHandler.save_int(dataHandler.namespaceKey_Fuel,data,fuel+fuel_add-(1+DataHandler.get_int(dataHandler.namespaceKey_Overclock,data)));
                        }
                        else {
                            DataHandler.change_bool(dataHandler.namespaceKey_Pause,data,player,null);
                            player.sendMessage(ChatColor.DARK_RED+"---------------------");
                            player.sendMessage(ChatColor.RED+"There is no fuel left! So placer was paused!");
                            player.sendMessage(ChatColor.DARK_RED+"---------------------");
                            DataHandler.save_string(dataHandler.namespaceKey_Task_Last_Message,data,"There is no fuel left! So placer was paused!");
                            return;
                        }
                    }
                }
                else {
                    DataHandler.change_bool(dataHandler.namespaceKey_Pause,data,player,null);
                    player.sendMessage(ChatColor.DARK_RED+"---------------------");
                    player.sendMessage(ChatColor.RED+"The hopper is missing! So placer was paused!");
                    player.sendMessage(ChatColor.DARK_RED+"---------------------");
                    DataHandler.save_string(dataHandler.namespaceKey_Task_Last_Message,data,"The hopper is missing! So placer was paused!");
                    return;
                }
                if(new Chest().exists(chest_pos)){
                    if(material_current_block.isAir() || current_block.isLiquid()){
                        Material item = new Chest().use_items(chest_pos,current_block);
                        if(item != null){
                            current_block.setType(item,true);
                            if(current_pos.getBlock().getBlockData() instanceof MultipleFacing){
                                Block_c.change_orientation(current_pos);
                            }
                        }
                        else {
                            DataHandler.change_bool(dataHandler.namespaceKey_Pause,data,player,null);
                            player.sendMessage(ChatColor.DARK_RED+"---------------------");
                            player.sendMessage(ChatColor.RED+"The chest has no items! So placer was paused!");
                            player.sendMessage(ChatColor.DARK_RED+"---------------------");
                            DataHandler.save_string(dataHandler.namespaceKey_Task_Last_Message,data,"The chest has no items! So placer was paused!");
                            return;
                        }
                    }
                }
                else {
                    DataHandler.change_bool(dataHandler.namespaceKey_Pause,data,player,null);
                    player.sendMessage(ChatColor.DARK_RED+"---------------------");
                    player.sendMessage(ChatColor.RED+"The chest is missing! So placer was paused!");
                    player.sendMessage(ChatColor.DARK_RED+"---------------------");
                    DataHandler.save_string(dataHandler.namespaceKey_Task_Last_Message,data,"The chest is missing! So placer was paused!");
                    return;
                }
                double blocks = DataHandler.get_double(dataHandler.namespaceKey_Blocks_Remaining,data) - 1;
                DataHandler.save_double(dataHandler.namespaceKey_Blocks_Remaining,data,blocks);
                //STOP IF CURRENT POS IS POS2
                if(current_pos.getX()==pos2.getX() && current_pos.getY()==pos2.getY() && current_pos.getZ()==pos2.getZ()) {
                    DataHandler.change_bool(dataHandler.namespaceKey_Running,data,player,null);
                    player.sendMessage(ChatColor.DARK_RED+"---------------------");
                    player.sendMessage(ChatColor.GREEN+"Placer has finished!");
                    player.sendMessage(ChatColor.DARK_RED+"---------------------");
                    DataHandler.save_string(dataHandler.namespaceKey_Task_Last_Message,data,"Placer has finished!");
                    return;
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
                int TIME_l = 0;
                if(current_pos.getBlock().getType().isAir() || current_pos.getBlock().isLiquid()){
                    TIME_l = placer.getConfig().getInt("Time") - (DataHandler.get_int(dataHandler.namespaceKey_Overclock,data)+1);
                }
                DataHandler.save_position(dataHandler.namespacesKey_PosCurrent,data,current_pos);
                DataHandler.save_int(dataHandler.namespaceKey_Task_Next_Time,data,TIME_l);
                DataHandler.save_string(dataHandler.namespaceKey_Task_Last_Message,data,"");
                run_t(player,TIME_l);
            }
        }.runTaskLater(plugin, TIME*20).getTaskId();
        DataHandler.save_int(dataHandler.namespaceKey_Task_Id,data,ID);
    }
}
