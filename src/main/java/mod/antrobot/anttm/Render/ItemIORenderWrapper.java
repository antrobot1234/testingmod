package mod.antrobot.anttm.Render;

import mod.antrobot.anttm.capabilities.standardio.EnumIO.EnumDir;
import mod.antrobot.anttm.capabilities.standardio.EnumIO.EnumType;
import mod.antrobot.anttm.capabilities.standardio.IStandardIO;
import mod.antrobot.anttm.items.ItemIO;
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
    public void drawTriangle(BufferBuilder buf, EnumType type, EnumDir dir,boolean isCurrent){
        final float size = .1f;
        final float gui = .5f;
        int x = 0;
        int y = 0;
        float shift = 0;
        switch(dir){
            case up: y = 1;break;
            case down: y = -1;break;
            case right: x = 1;break;
            case left: x = -1;
        }
        if(type==EnumType.output)shift = size;
        putIOColor(buf.pos((x*gui)+(y*size)-(shift*x),(y*gui)+(x*size)-(shift*y),0).tex(0,0),type,isCurrent).endVertex();
        putIOColor(buf.pos((x*gui)-(y*size)-(shift*x),(y*gui)-(x*size)-(shift*y),0).tex(0,0),type,isCurrent).endVertex();
        putIOColor(buf.pos((x*gui)-(x*size)+(shift*x),(y*gui)-(y*size)+(shift*y),0).tex(0,0),type,isCurrent).endVertex();
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
        GlStateManager.disableLighting();
        buf.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR);

        IStandardIO io = stack.getCapability(ItemIO.IO_CAPABILITY,null);
        EnumType type;
        for(EnumDir dir: EnumDir.values()){
            if(dir == EnumDir.nil)continue;
            if(io==null)continue;
            type = io.getFace(dir);
            if(type==EnumType.nil)continue;
            drawTriangle(buf,type,dir,io.getCurrent(type)==dir);
        }


        tes.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
