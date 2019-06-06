package mod.antrobot.anttm.Render;

import mod.antrobot.anttm.capabilities.standardio.EnumIO.EnumType;
import mod.antrobot.anttm.util.ModItemList;
import mod.antrobot.anttm.util.ModReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;


public class ItemIORenderWrapper extends TileEntityItemStackRenderer {
    public BufferBuilder putIOColor(BufferBuilder buf, EnumType type,boolean isCurrent){
        if(isCurrent){
            if(type == EnumType.input){
                return buf.color(0,100,255,255);
            } else {
                return buf.color(255,150,0,255);
            }
        } else if(type == EnumType.input){
            return buf.color(0f,0,255,255);
        } else {
            return buf.color(200,100,0,255);
        }
    }
    public static ModelResourceLocation getResourceLocation(String name){
        return new ModelResourceLocation(new ResourceLocation(ModReference.ID,name),"inventory");
    }
    //todo
    //  method that takes in the tessleator buffer and renders a triangle given three characteristics: color (I,O) facing(I,O) direction(EnumDir)
    @Override
    public void renderByItem(ItemStack stack) {

        //render base model
        RenderItem renderer = Minecraft.getMinecraft().getRenderItem();
        Item item = stack.getItem();
        ModelManager manager = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelManager();
        IBakedModel model =  manager.getModel(getResourceLocation(ModItemList.ITEMS.get(item)));
        renderer.renderModel(ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.NONE,false),-1,stack);

        Tessellator tes = Tessellator.getInstance();
        BufferBuilder buf = tes.getBuffer();
        GlStateManager.pushMatrix();
        GlStateManager.translate(.5f,.5f,.5f);
        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();
        buf.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR);

        putIOColor(buf.pos(.5,.5,0).tex(0,0),EnumType.input,true).endVertex();
        putIOColor(buf.pos(0,0,0).tex(0,0),EnumType.input,true).endVertex();
        putIOColor(buf.pos(.5,-.5,0).tex(0,0),EnumType.input,true).endVertex();

        //todo actually render arrows
        /*
        IStandardIO io = stack.getCapability(ItemIO.IO_CAPABILITY,null);
        buf.putPosition(0,0,0);
        buf.putPosition(1,1,0);
        buf.endVertex();
         */
        /*for(EnumIO.EnumDir dir: EnumIO.EnumDir.values()){
            if(dir == EnumIO.EnumDir.nil)return;
        }*/


        tes.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }
}
