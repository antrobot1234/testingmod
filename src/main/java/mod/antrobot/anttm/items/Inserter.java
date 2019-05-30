package mod.antrobot.anttm.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Inserter extends Item {


    public Inserter() {
        setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean held) {
        super.onUpdate(stack, world, player, slot, held);

        if (slot % 9 == 0) return;
        if (slot % 9 == 8) return;

        InventoryPlayer inventory = ((EntityPlayer) player).inventory;

        ItemStack inStack = inventory.getStackInSlot(slot - 1);
        if (inStack.isEmpty()) return;

        ItemStack outStack = inventory.getStackInSlot(slot+1);
        if(!outStack.isEmpty()) {
            if (outStack.getCount() == outStack.getMaxStackSize()) return;
            if (!inStack.isItemEqual(outStack)) return;
            if(!ItemStack.areItemStackTagsEqual(inStack,outStack))return;
            outStack.grow(1);
            inStack.shrink(1);
        }
        else{
            ItemStack stackClone = inStack.splitStack(1);
            inventory.setInventorySlotContents(slot+1,stackClone);
            inventory.getStackInSlot(slot+1).setCount(1);
        }
        ((EntityPlayer) player).inventoryContainer.detectAndSendChanges();
    }
}
