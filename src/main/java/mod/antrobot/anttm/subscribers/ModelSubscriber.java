package mod.antrobot.anttm.subscribers;

import mod.antrobot.anttm.util.ModReference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

@Mod.EventBusSubscriber(modid = ModReference.ID, value = CLIENT)
public class ModelSubscriber {

    @SubscribeEvent
    public static void onRegisterModelsEvent(ModelRegistryEvent event){
        registerTileEntitySpecialRenderers();
        registerEntityRenderers();

        ForgeRegistries.ITEMS.getValuesCollection().stream()
                .filter(item -> item.getRegistryName().getNamespace().equals(ModReference.ID))
                .filter(ModelSubscriber::hasNormalModel)
                .forEach(item -> {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        });
    }
    private static boolean hasNormalModel(Item item){
        return true;
    }
    private static void registerTileEntitySpecialRenderers() {
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityExampleTileEntity.class, new RenderExampleTileEntity());
    }
    private static void registerEntityRenderers() {
        //RenderingRegistry.registerEntityRenderingHandler(Entity___.class, Entity___Renderer::new);
    }
    private static void registerSpecialItemRenderers(){
    }
}
