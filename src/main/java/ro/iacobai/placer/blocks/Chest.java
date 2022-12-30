package ro.iacobai.placer.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;



public class Chest {
    public boolean exists(Location chest_pos){
        Material material = chest_pos.getBlock().getBlockData().getMaterial();
        if(!material.equals(Material.CHEST)){
            return false;
        }
        return true;
    }
    public Material use_items(Location chest_pos){
        org.bukkit.block.Chest chest_b = (org.bukkit.block.Chest) chest_pos.getBlock().getState();
        ItemStack[] items = chest_b.getInventory().getContents();
        for(int i = 0;i<items.length;i++){
            if(items[i]!=null){
                Material material = items[i].getType();
                if(material.getCreativeCategory().name()=="BUILDING_BLOCKS"){
                    items[i].setAmount(items[i].getAmount()-1);
                    return material;
                }
            }
        }
        return null;
    }
}
