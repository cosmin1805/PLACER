package ro.iacobai.placer.blocks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class Hopper {
    public enum Fuel {
        LAVA_BUCKET(Material.LAVA_BUCKET, 133),
        COAL_BLOCK(Material.COAL_BLOCK, 106),
        DRIED_KELP_BLOCK(Material.DRIED_KELP_BLOCK,26),
        BLAZE_ROD(Material.BLAZE_ROD, 16),
        COAL(Material.COAL,11),
        CHARCOLE(Material.CHARCOAL,11);


        private Material type;
        private int burntime;

        Fuel(Material type, int burntime) {
            this.type = type;
            this.burntime = burntime;
        }
    }
    public int value_fuel(Material material){
        Fuel[] fuels = Fuel.values();
        for (Fuel fuel_c : fuels)
        {
            if (fuel_c.type==material)
            {
                return fuel_c.burntime;
            }
        }
        return 1;
    }
    public int Fuel_check(org.bukkit.block.Hopper hopper_data){
        int fuel = 0;
        for(int i = 0;i<5;i++)
        {
            ItemStack item = hopper_data.getInventory().getItem(i);
            if(item != null)
            {
                Material material = item.getType();
                if(material.isFuel()){
                    fuel+=value_fuel(material)*item.getAmount();
                    hopper_data.getInventory().remove(item);
                }
            }
        }
        return fuel;
    }

}
