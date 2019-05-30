package mod.antrobot.anttm.items;

import mod.antrobot.anttm.capabilities.basicio.IBasicIO;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class Inserter extends Item{


    public Inserter() {
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
        playerIn.sendStatusMessage(new TextComponentString(io.getInput()),true);
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean held) {
        super.onUpdate(stack, world, player, slot, held);

        IBasicIO io = stack.getCapability(IO_CAPABILITY,null);

        if(io.isInvalid(slot))return;

        InventoryPlayer inventory = ((EntityPlayer) player).inventory;
        int inSlot = io.calcInput(slot);

        ItemStack inStack = inventory.getStackInSlot(inSlot);
        if (inStack.isEmpty()) return;
        int outSlot = io.calcOutput(slot);
        ItemStack outStack = inventory.getStackInSlot(outSlot);
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
            inventory.getStackInSlot(outSlot).setCount(1);
        }
        ((EntityPlayer) player).inventoryContainer.detectAndSendChanges();
    }

    @Override
    public ICapabilitySerializable<NBTTagCompound> initCapabilities(ItemStack stack, NBTTagCompound nbt)
    {
        IBasicIO instance = IO_CAPABILITY.getDefaultInstance();
        return new ICapabilitySerializable<NBTTagCompound>() {
            @Override
            public NBTTagCompound serializeNBT(){
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setString("input",instance.getInput());
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
}
