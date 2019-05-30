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
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class Inserter extends Item{


    public Inserter() {
        setMaxStackSize(1);
    }

    @CapabilityInject(IBasicIO.class)
    static Capability<IBasicIO> IO_CAPABILITY = null;

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
        stack.getCapability(IO_CAPABILITY,null).setOutput(true);
        stack.getCapability(IO_CAPABILITY,null).setInput("left");
    }
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

        if(!io.isValid(slot))return;

        InventoryPlayer inventory = ((EntityPlayer) player).inventory;

        ItemStack inStack = inventory.getStackInSlot(io.calcInput(slot));
        if (inStack.isEmpty()) return;

        ItemStack outStack = inventory.getStackInSlot(io.calcOutput(slot));
        if(!outStack.isEmpty()) {
            if (outStack.getCount() == outStack.getMaxStackSize()) return;
            if (!inStack.isItemEqual(outStack)) return;
            if(!ItemStack.areItemStackTagsEqual(inStack,outStack))return;
            outStack.grow(1);
            inStack.shrink(1);
        }
        else{
            ItemStack stackClone = inStack.splitStack(1);
            inventory.setInventorySlotContents(io.calcOutput(slot),stackClone);
            inventory.getStackInSlot(io.calcOutput(slot)).setCount(1);
        }
        ((EntityPlayer) player).inventoryContainer.detectAndSendChanges();
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack,NBTTagCompound nbt)
    {
        return new ICapabilityProvider() {
            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                if(capability == IO_CAPABILITY)return true;
                return false;
            }

            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                if(capability == IO_CAPABILITY) return (T)IO_CAPABILITY;
                return null;
            }
        };
    }
}
