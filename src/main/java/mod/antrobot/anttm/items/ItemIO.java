package mod.antrobot.anttm.items;

import mod.antrobot.anttm.capabilities.standardio.EnumIO.EnumDir;
import mod.antrobot.anttm.capabilities.standardio.EnumIO.EnumType;
import mod.antrobot.anttm.capabilities.standardio.IStandardIO;
import mod.antrobot.anttm.capabilities.standardio.StandardProvider;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import java.util.HashSet;

public abstract class ItemIO extends ItemTooltip{
    protected int speed = 20;
    private EnumType up;
    private EnumType right;
    private EnumType down;
    private EnumType left;
    protected boolean isStatic;
    protected HashSet<Item> inputBlacklist = new HashSet<>();

    @CapabilityInject(IStandardIO.class)
    public static Capability<IStandardIO> IO_CAPABILITY = null;

    public ItemIO(String tip, EnumType up, EnumType right, EnumType down , EnumType left, boolean isStatic) {
        super(tip);
        this.up = up;
        this.right = right;
        this.down = down;
        this.left= left;
        this.isStatic = isStatic;
        setMaxStackSize(1);
    }

    protected boolean isBlacklisted(ItemStack item){
        if(item.getItem() instanceof ItemIO)return true;
        return inputBlacklist.contains(item.getItem());
    }
    abstract boolean isIPossible(ItemStack input, ItemStack worker);

    protected boolean canInput(ItemStack input,ItemStack worker){
        if(input.isEmpty())return false;
        if(isBlacklisted(input))return false;
        if(!isIPossible(input,worker))return false;
        return true;
    }

    abstract boolean isOPossible(ItemStack output, ItemStack worker);

    protected boolean canFit(ItemStack input, ItemStack output){
        if(output.isEmpty())return true;
        if(output.getCount() >= output.getMaxStackSize()) return false;
        if(!output.isItemEqual(input))return false;
        if(!ItemStack.areItemStackTagsEqual(input,output))return false;
        return true;
    }

    protected boolean canOutput(ItemStack output, ItemStack worker){
        return isOPossible(output,worker);
    }
    abstract boolean customIORule(ItemStack input, ItemStack worker, ItemStack output);

    protected boolean canIO(ItemStack input, ItemStack worker, ItemStack output){
        if(!canInput(input,worker))return false;
        if(!canOutput(input,worker))return false;
        if(!canFit(input,output))return false;
        return customIORule(input,output,worker);
    }
    abstract ItemStack transform(ItemStack input);
    abstract ItemStack take(InventoryPlayer inventory,ItemStack item,ItemStack worker);
    abstract void put(InventoryPlayer inventory,ItemStack item,ItemStack worker,EnumDir face,int slot);

    protected ItemStack get(InventoryPlayer inventory,EnumDir face,ItemStack worker,int slot){
        IStandardIO io = worker.getCapability(IO_CAPABILITY,null);
        if(io == null)return null;
        return inventory.getStackInSlot(io.calcFaceExtend(inventory,slot,face));
    }


    @Override
    public ICapabilitySerializable<NBTTagCompound> initCapabilities(ItemStack stack, NBTTagCompound nbt){
        return StandardProvider.provide(up,right,down,left);
    }
    //TODO before doing this i must learn (Rendering)
    //TODO add following:
    // on right click switch method with Rotate or Cycle/rotate (dependant on if static)
    // Whitelist (in separate subclass)
}
