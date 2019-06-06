package mod.antrobot.anttm.subscribers;

import mod.antrobot.anttm.Render.ItemIORenderWrapper;
import mod.antrobot.anttm.util.ModItemList;
import mod.antrobot.anttm.util.ModReference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import mod.antrobot.anttm.items.ItemIO;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

@Mod.EventBusSubscriber(modid = ModReference.ID, value = CLIENT)
public class ModelSubscriber {

    @SubscribeEvent
    public static void onRegisterModelsEvent(ModelRegistryEvent event) {
        registerTileEntitySpecialRenderers();
        registerEntityRenderers();
        registerSpecialItemRenderers();
        for (Item item: ModItemList.getItemList()){
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
            if (item instanceof ItemIO) {
                registerSpecialRenderer(item);
            }
        }
    }
    private static void registerSpecialRenderer(Item item){
        if(item instanceof ItemIO){
            item.setTileEntityItemStackRenderer(new ItemIORenderWrapper());
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(ModReference.ID,"item_io"),"inventory"));
        }
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
