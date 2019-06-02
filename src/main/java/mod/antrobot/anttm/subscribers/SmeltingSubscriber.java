package mod.antrobot.anttm.subscribers;

import mod.antrobot.anttm.util.ModReference;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(ModReference.ID)
public class SmeltingSubscriber {
    //add items here
    //public static final Item test = null;
    public static void registerSmeltingRecipes(){
        //register items here
        //GameRegistry.addSmelting(test,new ItemStack(Items.IRON_INGOT),0.1F);
    }
}
