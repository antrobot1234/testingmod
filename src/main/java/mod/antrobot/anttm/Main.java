package mod.antrobot.anttm;

import mod.antrobot.anttm.capabilities.standardio.IStandardIO;
import mod.antrobot.anttm.capabilities.standardio.StandardFactory;
import mod.antrobot.anttm.capabilities.standardio.StandardStorage;
import mod.antrobot.anttm.subscribers.SmeltingSubscriber;
import mod.antrobot.anttm.util.ModReference;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = ModReference.ID,name = ModReference.NAME,version = ModReference.VERSION)
public class Main {
    public static Main instance;
    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event){
        CapabilityManager.INSTANCE.register(IStandardIO.class,new StandardStorage(), new StandardFactory());
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event){
        SmeltingSubscriber.registerSmeltingRecipes();

    }
}
