package mod.antrobot.anttm;
import mod.antrobot.anttm.capabilities.basicio.BasicFactory;
import mod.antrobot.anttm.capabilities.basicio.BasicStorage;
import mod.antrobot.anttm.capabilities.basicio.IBasicIO;
import mod.antrobot.anttm.subscribers.SmeltingSubscriber;
import mod.antrobot.anttm.util.ModReference;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = ModReference.ID,name = ModReference.NAME,version = ModReference.VERSION)
public class Main {
    public static Main instance;
    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event){
        CapabilityManager.INSTANCE.register(IBasicIO.class,new BasicStorage(), new BasicFactory());
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event){
        SmeltingSubscriber.registerSmeltingRecipes();

    }
}
