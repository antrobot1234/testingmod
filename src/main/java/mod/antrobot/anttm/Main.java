package mod.antrobot.anttm;
import mod.antrobot.anttm.subscribers.SmeltingSubscriber;
import mod.antrobot.anttm.util.ModReference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;


@Mod(modid = ModReference.ID,name = ModReference.NAME,version = ModReference.VERSION)
public class Main {
    public static Main instance;
    @Mod.EventHandler
    public static void init(FMLInitializationEvent event){
        SmeltingSubscriber.registerSmeltingRecipes();
    }
}
