package mod.antrobot.anttm.util;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModItemList {
    public static final HashMap<Item,String> ITEMS = new HashMap<>();
    public static ArrayList<Item> getItemList(){
        ArrayList<Item> output = new ArrayList<>();
        for(Map.Entry<Item,String> entry:ITEMS.entrySet()){
            output.add(entry.getKey());
        }
        return output;
    }
}
