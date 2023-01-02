package ro.iacobai.placer.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.MultipleFacing;

public class Block_c {
    public static void change_orientation(Location current_pos){
        Block current_block = current_pos.getBlock();
        BlockData blockData = current_block.getBlockData();
        MultipleFacing multipleFacing = (MultipleFacing) blockData;
        World world = Bukkit.getWorld("world");
        int x = (int)current_block.getX();
        int y = (int)current_block.getY();
        int z = (int)current_block.getZ();
        Material north = new Location(world,x,y,z-1).getBlock().getType();
        Material  south = new Location(world,x,y,z+1).getBlock().getType();
        Material  west = new Location(world,x-1,y,z).getBlock().getType();
        Material  east = new Location(world,x+1,y,z).getBlock().getType();
        if(!north.isAir()){
            multipleFacing.setFace(BlockFace.NORTH,true);
        }
        if(!south.isAir()){
            multipleFacing.setFace(BlockFace.SOUTH,true);
        }
        if(!west.isAir()){
            multipleFacing.setFace(BlockFace.WEST,true);
        }
        if(!east.isAir()){
            multipleFacing.setFace(BlockFace.EAST,true);
        }
        current_pos.getBlock().setBlockData(blockData);
    }
}
