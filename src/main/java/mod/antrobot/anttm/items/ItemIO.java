package mod.antrobot.anttm.items;

import mod.antrobot.anttm.capabilities.basicio.BasicProvider;
import mod.antrobot.anttm.capabilities.basicio.IBasicIO;
import mod.antrobot.anttm.util.ModReference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashSet;

public class ItemIO extends ItemTooltip{
    @CapabilityInject(IBasicIO.class)
    private static Capability<IBasicIO> IO_CAPABILITY = null;
    private String input;
    private final boolean output;
    private int speed;
    private boolean hasCustomModels;
    protected HashSet<Item> inputBlacklist = new HashSet<>();

    @GameRegistry.ObjectHolder(ModReference.ID+":extender")
    private static final Item extender = null;

    public ItemIO(String tooltip,String input, Boolean output,int speed,boolean hasCustomModels){
        super(tooltip);
        this.input = input;
        this.output = output;
        this.speed = speed;
        this.hasCustomModels = hasCustomModels;
        setMaxStackSize(1);
        inputBlacklist.add(extender);
    }

    public boolean isOutput(){return output;}

    protected boolean isInputInvalid(ItemStack input){
        if(input.getItem() instanceof ItemIO)return true;
        return inputBlacklist.contains(input.getItem());
    }

    protected boolean ioValid(ItemStack inStack, ItemStack outStack){
        if(outStack.getCount() == outStack.getMaxStackSize()) return false;
        if(!inStack.isItemEqual(outStack))return false;
        if(!ItemStack.areItemStackTagsEqual(inStack,outStack))return false;
        return true;
    }

    protected static ItemStack transform(ItemStack inStack){return  inStack;}
    protected void consume(InventoryPlayer inventory, ItemStack inStack){
        if(isInputInvalid(inStack)){
            inStack.shrink(1);
            transform(inStack);
        }
    }
    protected void translate(InventoryPlayer inventory, ItemStack inStack, ItemStack outStack, int outSlot){
        if(!outStack.isEmpty()){
            if(!ioValid(transform(inStack),outStack))return;
            outStack.grow(1);
            inStack.shrink(1);
        }
        else{
            ItemStack stackClone = inStack.splitStack(1);
            stackClone = transform(stackClone);
            inventory.setInventorySlotContents(outSlot,stackClone);
        }
        inventory.player.inventoryContainer.detectAndSendChanges();
    }

    protected boolean canSuccess(InventoryPlayer inventory, ItemStack stack, World world, Entity player, int slot, boolean held){return true;}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ActionResult<ItemStack> output = new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
        ItemStack item = playerIn.getHeldItem(handIn);
        IBasicIO io = item.getCapability(IO_CAPABILITY,null);
        if(io == null)return output;
        io.cycleInput();
        if(!hasCustomModels){playerIn.sendStatusMessage(new TextComponentString("input face: "+io.getInput()),true);}
        return output;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean held) {
        super.onUpdate(stack, world, player, slot, held);
        if (world.isRemote) return;
        if (world.getTotalWorldTime() % speed != 0) return;

        IBasicIO io = stack.getCapability(IO_CAPABILITY, null);
        if (io == null) return;
        if (io.isInvalid(slot)) return;

        InventoryPlayer inventory = ((EntityPlayer) player).inventory;
        int inSlot = io.calcInput(slot);
        ItemStack inStack = inventory.getStackInSlot(inSlot);
        if (inStack.isEmpty()) return;
        if (isInputInvalid(inStack)) return;
        if (canSuccess(inventory, stack, world, player, slot, held)){
            if (output) {
                int outSlot = io.calcOutput(slot);
                ItemStack outStack = inventory.getStackInSlot(outSlot);
                if (outStack.getItem() == extender) {
                    forward(inventory, stack, inStack,outSlot,1);
                } else {
                    translate(inventory, inStack, outStack, outSlot);
                }
            } else {
                consume(inventory, inStack);
            }
        }
    }
    private void forward(InventoryPlayer inventory,ItemStack firstWorker,ItemStack inStack,int currentSlot,int count){
        if(count >= 10)return;

        IBasicIO io = firstWorker.getCapability(IO_CAPABILITY,null);
        if(io == null)return;

        if(io.isInvalid(currentSlot))return;

        int outSlot = io.calcOutput(currentSlot);
        ItemStack outStack = inventory.getStackInSlot(outSlot);
        if(outStack.getItem()==extender){
            forward(inventory,firstWorker,inStack,outSlot,count+1);
        }
        else{
            translate(inventory,inStack,outStack,outSlot);
        }
    }

    @Override
    public ICapabilitySerializable<NBTTagCompound> initCapabilities(ItemStack stack, NBTTagCompound nbt){
        return BasicProvider.provide(input,output);
    }
}
