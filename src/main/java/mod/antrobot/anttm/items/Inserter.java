package mod.antrobot.anttm.items;

import mod.antrobot.anttm.capabilities.basicio.BasicFactory;
import mod.antrobot.anttm.capabilities.basicio.IBasicIO;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Inserter extends ItemTooltip{
    @GameRegistry.ObjectHolder("anttm:extender")
    public static final Item extender = null;

    public Inserter(String tooltip) {
        super(tooltip);
        setMaxStackSize(1);
    }

    @CapabilityInject(IBasicIO.class)
    private static Capability<IBasicIO> IO_CAPABILITY = null;

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack item = playerIn.getHeldItem(handIn);
        IBasicIO io = item.getCapability(IO_CAPABILITY,null);
        io.cycleInput();
        playerIn.sendStatusMessage(new TextComponentString("pulling from: "+io.getInput()),true);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean held) {
        super.onUpdate(stack, world, player, slot, held);
        if(world.isRemote)return;
        if(world.getTotalWorldTime() % 20 != 0)return;

        IBasicIO io = stack.getCapability(IO_CAPABILITY,null);

        if(io.isInvalid(slot))return;

        InventoryPlayer inventory = ((EntityPlayer) player).inventory;
        int inSlot = io.calcInput(slot);

        ItemStack inStack = inventory.getStackInSlot(inSlot);
        if (inStack.isEmpty()) return;
        if (inStack.getItem()==this)return;
        int outSlot = io.calcOutput(slot);
        ItemStack outStack = inventory.getStackInSlot(outSlot);
        if(outStack.getItem()==this){
            forward(inventory,inStack,outStack,outSlot,1);
        }
        else{
        insert(inventory,inStack,outStack,outSlot);
        }
    }
    public void forward(InventoryPlayer inventory,ItemStack inStack, ItemStack currentWorker,int currentSlot,int count){
        if(count >= 10)return;
        if(inStack==currentWorker)return;
        IBasicIO io = currentWorker.getCapability(IO_CAPABILITY,null);
        if(io.isInvalid(currentSlot))return;
        int outSlot = io.calcOutput(currentSlot);
        ItemStack outStack = inventory.getStackInSlot(outSlot);
        if(outStack != currentWorker && outStack.getItem()==this){
            forward(inventory,inStack,outStack,outSlot,count+1);
            //TODO see and fix why the fuck this crashed the game with an infinte loop (i mean yeah recursion but seriously)
            return;
        }
        else{
            insert(inventory,inStack,outStack,outSlot);
        }
    }
    public void insert(InventoryPlayer inventory,ItemStack inStack, ItemStack outStack, int outSlot){
        if(!outStack.isEmpty()) {
            if (outStack.getCount() == outStack.getMaxStackSize()) return;
            if (!inStack.isItemEqual(outStack)) return;
            if(!ItemStack.areItemStackTagsEqual(inStack,outStack))return;
            outStack.grow(1);
            inStack.shrink(1);
        }
        else{
            ItemStack stackClone = inStack.splitStack(1);
            inventory.setInventorySlotContents(outSlot,stackClone);
        }
        inventory.player.inventoryContainer.detectAndSendChanges();
    }

    @Override
    public ICapabilitySerializable<NBTTagCompound> initCapabilities(ItemStack stack, NBTTagCompound nbt)
    {
        IBasicIO instance = IO_CAPABILITY.getDefaultInstance();
        return new ICapabilitySerializable<NBTTagCompound>() {
            @Override
            public NBTTagCompound serializeNBT(){
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setString("input",instance.getInput().toString());
                nbt.setBoolean("output",instance.getOutput());
                return nbt;
            }
            @Override
            public void deserializeNBT(NBTTagCompound nbt){
                instance.setInput(nbt.getString("input"));
                instance.setOutput(nbt.getBoolean("output"));
            }

            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                if(capability == IO_CAPABILITY)return true;
                return false;
            }

            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                if(capability == IO_CAPABILITY) return IO_CAPABILITY.<T> cast(instance);
                return null;
            }
        };
    }

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
