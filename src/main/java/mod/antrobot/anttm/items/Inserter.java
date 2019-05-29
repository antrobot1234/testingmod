package mod.antrobot.anttm.items;

import com.sun.istack.internal.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class Inserter extends Item {

    @CapabilityInject(IItemHandler.class)
    public static Capability<IItemHandler> ITEM_HANDLER;

    public Inserter(){
        setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean held) {
        super.onUpdate(stack, world, player, slot, held);
    }
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return new ICapabilityProvider() {
            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                return capability == ITEM_HANDLER;
            }

            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                if(capability == ITEM_HANDLER) return (T) ITEM_HANDLER;
                return null;
            }
        };
    }

}
