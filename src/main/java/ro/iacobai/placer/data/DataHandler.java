package ro.iacobai.placer.data;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import ro.iacobai.placer.PLACER;


public class DataHandler {

    //ALL THE "BOOLEANS"
    public NamespacedKey namespacesKey_Pos_Select = new NamespacedKey(PLACER.getPlugin(),"placer_pos_select");
    public NamespacedKey namespaceKey_Await_Confirm = new NamespacedKey(PLACER.getPlugin(),"placer_await_confirm");
    public NamespacedKey namespaceKey_Running = new NamespacedKey(PLACER.getPlugin(),"placer_running");
    public NamespacedKey namespaceKey_Pause = new NamespacedKey(PLACER.getPlugin(),"placer_pause");
    public NamespacedKey namespaceKey_Highlight = new NamespacedKey(PLACER.getPlugin(),"placer_highlight");
    //ALL THE LOCATIONS
    public NamespacedKey namespaceKey_Pos1 = new NamespacedKey(PLACER.getPlugin(),"placer_pos1");
    public NamespacedKey namespaceKey_Pos2 = new NamespacedKey(PLACER.getPlugin(),"placer_pos2");
    public NamespacedKey namespaceKey_PosChest = new NamespacedKey(PLACER.getPlugin(),"placer_chest_pos");
    public NamespacedKey namespaceKey_PosHopper = new NamespacedKey(PLACER.getPlugin(),"placer_hopper_pos");
    public NamespacedKey namespacesKey_PosCurrent = new NamespacedKey(PLACER.getPlugin(),"placer_current_pos");
    //ALL THE INTEGERS AND DOUBLE DATA
    public NamespacedKey namespaceKey_Blocks_Remaining = new NamespacedKey(PLACER.getPlugin(),"placer_blocks_remaining");
    public NamespacedKey namespaceKey_Fuel = new NamespacedKey(PLACER.getPlugin(),"placer_fuel");
    public NamespacedKey namespaceKey_Overclock = new NamespacedKey(PLACER.getPlugin(),"placer_overclock");
    public NamespacedKey namespaceKey_Task_Next_Time = new NamespacedKey(PLACER.getPlugin(),"placer_next_block_time");
    public NamespacedKey namespaceKey_Task_Id = new NamespacedKey(PLACER.getPlugin(),"placer_id");
    public NamespacedKey namespaceKey_Task_Particle_Id = new NamespacedKey(PLACER.getPlugin(),"placer_particle_id");
    //string
    public NamespacedKey namespaceKey_Task_Last_Message= new NamespacedKey(PLACER.getPlugin(),"placer_last_message");

    public static void change_bool(NamespacedKey namespacedKey, PersistentDataContainer data, Player player, String message) {
        if (!data.has(namespacedKey, PersistentDataType.INTEGER)) {
            data.set(namespacedKey, PersistentDataType.INTEGER, 0);
            if(message != null)
                player.sendMessage(ChatColor.RED + message + "OFF");
            return;
        }
        int data_select = data.get(namespacedKey, PersistentDataType.INTEGER);
        if (data_select == 1) {
            data.set(namespacedKey, PersistentDataType.INTEGER, 0);
            if(message != null)
                player.sendMessage(ChatColor.RED + message + "OFF");
        } else {
            data.set(namespacedKey, PersistentDataType.INTEGER, 1);
            if(message != null)
                player.sendMessage(ChatColor.GREEN + message + "ON");
        }
    }
    public  static int  get_bool(NamespacedKey namespacedKey, PersistentDataContainer data){
        if (!data.has(namespacedKey, PersistentDataType.INTEGER)) {
            data.set(namespacedKey, PersistentDataType.INTEGER, 0);
        }
        return data.get(namespacedKey, PersistentDataType.INTEGER);
    }
    public static void save_position(NamespacedKey namespacedKey, PersistentDataContainer data, Location location){
        int[] pos = new int[3];
        pos[0] = (int)location.getX();
        pos[1] = (int)location.getY();
        pos[2] = (int)location.getZ();
        data.set(namespacedKey, PersistentDataType.INTEGER_ARRAY, pos);
    }
    public static Location get_position(NamespacedKey namespacedKey, PersistentDataContainer data){
        World world = Bukkit.getWorld("world");
        if (!data.has(namespacedKey, PersistentDataType.INTEGER_ARRAY)) {
            Location location = new Location(world,0,0,0);
            save_position(namespacedKey,data,location);
            return location;
        }
        int[] pos = data.get(namespacedKey, PersistentDataType.INTEGER_ARRAY);
        Location location = new Location(world,pos[0],pos[1],pos[2]);
        return location;
    }
    public static void save_double(NamespacedKey namespacedKey, PersistentDataContainer data,double value){
        data.set(namespacedKey, PersistentDataType.DOUBLE, value);
    }
    public static double get_double(NamespacedKey namespacedKey, PersistentDataContainer data){
        if (!data.has(namespacedKey, PersistentDataType.DOUBLE)) {
            double value = 0;
            save_double(namespacedKey,data,value);
            return value;
        }
        double value = data.get(namespacedKey, PersistentDataType.DOUBLE);
        return value;
    }
    public static void save_int(NamespacedKey namespacedKey, PersistentDataContainer data,int value){
        data.set(namespacedKey, PersistentDataType.INTEGER, value);
    }
    public static int get_int(NamespacedKey namespacedKey, PersistentDataContainer data){
        if (!data.has(namespacedKey, PersistentDataType.INTEGER)) {
            int value = 0;
            save_int(namespacedKey,data,value);
            return value;
        }
        int value = data.get(namespacedKey, PersistentDataType.INTEGER);
        return value;
    }
    public static void save_string(NamespacedKey namespacedKey, PersistentDataContainer data,String value){
        data.set(namespacedKey, PersistentDataType.STRING, value);
    }
    public static String get_string(NamespacedKey namespacedKey, PersistentDataContainer data){
        if (!data.has(namespacedKey, PersistentDataType.STRING)) {
            save_string(namespacedKey,data,"");
            return "";
        }
        String value = data.get(namespacedKey, PersistentDataType.STRING);
        return value;
    }
}
