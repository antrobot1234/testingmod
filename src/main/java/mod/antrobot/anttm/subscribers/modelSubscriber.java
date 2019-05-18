package mod.antrobot.anttm.subscribers;

import mod.antrobot.anttm.util.ModReference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

@Mod.EventBusSubscriber(modid = ModReference.ID, value = CLIENT)
public class modelSubscriber {

    @SubscribeEvent
    public static void onRegisterModelsEvent(ModelRegistryEvent event){
        registerTileEntitySpecialRenderers();
        registerEntityRenderers();
        ForgeRegistries.ITEMS.getValues().stream()
                .filter(item -> item.getRegistryName().getNamespace().equals(ModReference.ID))
                .forEach(item -> {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        });
    }
    private static void registerTileEntitySpecialRenderers() {
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityExampleTileEntity.class, new RenderExampleTileEntity());
    }
    private static void registerEntityRenderers() {
        //RenderingRegistry.registerEntityRenderingHandler(Entity___.class, Entity___Renderer::new);
    }
}
