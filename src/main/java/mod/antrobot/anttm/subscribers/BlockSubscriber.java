package mod.antrobot.anttm.subscribers;


import mod.antrobot.anttm.util.ModReference;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ModReference.ID)
public class BlockSubscriber {
    @SubscribeEvent
    public static void onRegisterBlocksEvent(final RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                //blocks go here
        );
    }
}
