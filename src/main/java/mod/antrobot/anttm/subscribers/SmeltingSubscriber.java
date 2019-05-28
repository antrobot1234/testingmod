package mod.antrobot.anttm.subscribers;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import mod.antrobot.anttm.util.ModReference;

@GameRegistry.ObjectHolder(ModReference.ID)
public class SmeltingSubscriber {
    //add items here
    public static final Item test = null;
    public static void registerSmeltingRecipes(){
        //register items here
        GameRegistry.addSmelting(test,new ItemStack(Items.IRON_INGOT),0.1F);
    }
}
