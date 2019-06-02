package mod.antrobot.anttm.subscribers;

import mod.antrobot.anttm.util.ModReference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TextureStitch {
    @SubscribeEvent
    public static void stitch(TextureStitchEvent event){
        String ioLocation = ModReference.ID+":/items/io/arrow_";
        ModelResourceLocation in = new ModelResourceLocation(ioLocation+"input");
        ModelResourceLocation out = new ModelResourceLocation(ioLocation+"output");
        event.getMap().registerSprite(in);
        event.getMap().registerSprite(out);

    }
    @SubscribeEvent
    //TODO ask how this works and continue this part of the project
    public static void bake(ModelBakeEvent event){

    }
}
