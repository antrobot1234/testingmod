package mod.antrobot.anttm.capabilities.storagesize;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class SizeStorage implements Capability.IStorage<IStorageSize> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IStorageSize> capability, IStorageSize instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("height",instance.getHeight());
        nbt.setInteger("width",instance.getWidth());
        return nbt;
    }

    @Override
    public void readNBT(Capability<IStorageSize> capability, IStorageSize instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound tag = ((NBTTagCompound)nbt);
        instance.setHeight(tag.getInteger("height"));
        instance.setWidth(tag.getInteger("width"));
    }
}
