package mod.antrobot.anttm.items;

import mod.antrobot.anttm.capabilities.basicio.BasicFactory;
import mod.antrobot.anttm.capabilities.basicio.IBasicIO;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Inserter extends ItemIO{
    public Inserter(String tooltip) {
        super(tooltip,"left",true,20,true);
    }

    @CapabilityInject(IBasicIO.class)
    private static Capability<IBasicIO> IO_CAPABILITY = null;
    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelResourceLocation up = new ModelResourceLocation(getRegistryName()+"_up","inventory");
        ModelResourceLocation left = new ModelResourceLocation(getRegistryName()+"_left","inventory");
        ModelResourceLocation down = new ModelResourceLocation(getRegistryName()+"_down","inventory");
        ModelResourceLocation right = new ModelResourceLocation(getRegistryName()+"_right","inventory");

        ModelBakery.registerItemVariants(this,up,down,left,right);

        ModelLoader.setCustomMeshDefinition(this,new ItemMeshDefinition(){
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                BasicFactory.EnumDir input = stack.getCapability(IO_CAPABILITY,null).getInput();
                switch(input){
                    case up: return down;
                    case down: return up;
                    case right: return left;
                    default: return right;
                }
            }
        });
    }
}
