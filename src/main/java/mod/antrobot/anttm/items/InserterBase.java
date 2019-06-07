package mod.antrobot.anttm.items;

import mod.antrobot.anttm.capabilities.standardio.EnumIO.EnumDir;
import mod.antrobot.anttm.capabilities.standardio.EnumIO.EnumType;
import mod.antrobot.anttm.capabilities.standardio.IStandardIO;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class InserterBase extends ItemIO {


    public InserterBase(String tip, EnumType up, EnumType right, EnumType down, EnumType left, boolean isStatic) {
        super(tip, up, right, down, left, isStatic);
    }

    @Override
    ItemStack take(InventoryPlayer inventory, ItemStack item, ItemStack worker) {
        return item.splitStack(1);
    }

    @Override
    void put(InventoryPlayer inventory, ItemStack item, ItemStack worker,EnumDir face,int slot) {
        int outSlot = worker.getCapability(IO_CAPABILITY, null).calcFaceExtend(inventory, slot, face);
        ItemStack stack = inventory.getStackInSlot(outSlot);
        if (stack.isEmpty()) {
            inventory.setInventorySlotContents(outSlot, item);
        } else {
            stack.grow(1);
        }
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ActionResult<ItemStack> action = super.onItemRightClick(world, player, hand);
        IStandardIO io = player.getHeldItem(hand).getCapability(IO_CAPABILITY,null);
        if(io==null)return super.onItemRightClick(world, player, hand);
        if(isStatic || !io.getSides().containsValue(EnumType.nil)){
            io.rotate();
        } else if(player.isSneaking()){
            io.cycle(EnumType.output);
        } else io.cycle(EnumType.input);
        return action;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean held) {
        super.onUpdate(stack, world, player, slot, held);
        if (world.isRemote) return;
        if (world.getTotalWorldTime() % speed != 0) return;

        IStandardIO io = stack.getCapability(IO_CAPABILITY, null);
        if (io == null) return;
        InventoryPlayer inventory = ((EntityPlayer) player).inventory;
        if (!io.isValidSlot(inventory,slot)) return;
        EnumDir inFace = io.getNext(EnumType.input);
        EnumDir outFace = io.getNext(EnumType.output);
        if(inFace == EnumDir.nil && outFace == EnumDir.nil)return;
        ItemStack input;
        ItemStack output;
        if(inFace!= EnumDir.nil && outFace != EnumDir.nil){
            input = get(inventory,inFace,stack,slot);
            output = get(inventory,outFace,stack,slot);
            if(canIO(transform(input),stack,output)){
                put(inventory,transform(take(inventory,input,stack)),stack,outFace,slot);
            }
        }
        else if(inFace != EnumDir.nil){
            input = get(inventory,inFace,stack,slot);
            if(canInput(input,stack)){
                transform(take(inventory,input,stack));
            }
        }
        else{
            output = get(inventory,outFace,stack,slot);
            if(canIO(transform(stack),stack,output)){
                put(inventory,transform(stack),stack,outFace,slot);
            }
        }
    }
}
