package mod.antrobot.anttm.items;

import mod.antrobot.anttm.capabilities.standardio.EnumIO.EnumDir;
import mod.antrobot.anttm.capabilities.standardio.EnumIO.EnumType;
import mod.antrobot.anttm.capabilities.standardio.IStandardIO;
import mod.antrobot.anttm.capabilities.standardio.StandardProvider;
import mod.antrobot.anttm.util.ModReference;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashSet;

public abstract class ItemIOReplacement extends ItemTooltip{
    protected int speed = 20;
    private EnumType up;
    private EnumType right;
    private EnumType down;
    private EnumType left;
    protected boolean isStatic;

    protected HashSet<Item> inputBlacklist = new HashSet<>();
    @CapabilityInject(IStandardIO.class)

    @GameRegistry.ObjectHolder(ModReference.ID+":extender")
    protected static final Item extender = null;
    @CapabilityInject(IStandardIO.class)
    protected static Capability<IStandardIO> IO_CAPABILITY = null;
    public ItemIOReplacement(String tip, EnumType up, EnumType right, EnumType down ,EnumType left, boolean isStatic) {
        super(tip);
        this.up = up;
        this.right = right;
        this.down = down;
        this.left= left;
        this.isStatic = isStatic;
    }

    protected boolean isBlacklisted(ItemStack item){
        if(item.getItem() instanceof ItemIOReplacement)return false;
        return inputBlacklist.contains(item.getItem());
    }
    abstract boolean isIPossible(ItemStack input, ItemStack worker);

    protected boolean canInput(ItemStack input,ItemStack worker){
        if(isBlacklisted(input))return false;
        if(!isIPossible(input,worker))return false;
        return true;
    }

    abstract boolean isOPossible(ItemStack output, ItemStack worker);

    protected boolean canFit(ItemStack input, ItemStack output){
        if(output.isEmpty())return true;
        if(input.getCount() == output.getMaxStackSize()) return false;
        if(!output.isItemEqual(output))return false;
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
    protected final void putF(InventoryPlayer inventory,ItemStack item,ItemStack worker,EnumDir face,int slot){
        if(inventory.getStackInSlot(worker.getCapability(IO_CAPABILITY,null).calcFace(slot,face)).getItem()==extender){
            putF(inventory,item,worker,face,slot,1);
        }
        else {put(inventory,item,worker,face,slot);}
    }
    private final void putF(InventoryPlayer inventory,ItemStack item,ItemStack worker,EnumDir face,int slot,int count){
        if(count >= 10)return;

        IStandardIO io = worker.getCapability(IO_CAPABILITY,null);
        if(io == null)return;

        if(io.calcFace(slot,face)==-1)return;

        int outSlot = io.calcFace(slot,face);
        ItemStack outStack = inventory.getStackInSlot(outSlot);
        if(outStack.getItem()==extender){
            putF(inventory,worker,item,face,outSlot,count+1);
        }
        else{
            put(inventory,worker,item,face,outSlot);
        }
    }
    protected ItemStack get(InventoryPlayer inventory,EnumDir face,ItemStack worker,int slot){
        IStandardIO io = worker.getCapability(IO_CAPABILITY,null);
        if(io == null)return null;
        return inventory.getStackInSlot(io.calcFace(slot,face));
    }


    @Override
    public ICapabilitySerializable<NBTTagCompound> initCapabilities(ItemStack stack, NBTTagCompound nbt){
        return StandardProvider.provide(up,right,down,left);
    }
    //TODO before doing this i must learn (Rendering)
    //TODO add following:
    // item input and output methods (for use in onUpdate) using getNext()
    // on right click switch method with Rotate or Cycle/rotate (dependant on if static)
    // onUpdate that uses input and output methods
    // Whitelist (in separate subclass)
    // extender (item) support
    // input -> consume
    // output -> deliver
    //TODO keep in mind that this might or might not be used with things that input things into and out of them instead of directly with slots, so make methods workable or overridable in that sense
}
