package mod.antrobot.anttm.capabilities.standardio;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import mod.antrobot.anttm.capabilities.standardio.EnumIO.*;

import javax.annotation.Nullable;

public class StandardStorage implements Capability.IStorage<IStandardIO>{
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IStandardIO> capability, IStandardIO instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("up",instance.getFace(EnumDir.up).toString());
        nbt.setString("down",instance.getFace(EnumDir.down).toString());
        nbt.setString("left",instance.getFace(EnumDir.left).toString());
        nbt.setString("right",instance.getFace(EnumDir.right).toString());
        nbt.setString("current input",instance.getCurrent(EnumType.input).toString());
        nbt.setString("current output",instance.getCurrent(EnumType.output).toString());
        return nbt;
    }

    @Override
    public void readNBT(Capability<IStandardIO> capability, IStandardIO instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound inNBT = ((NBTTagCompound)nbt);
        instance.setDir(EnumDir.up,EnumType.valueOf(inNBT.getString("up")));
        instance.setDir(EnumDir.down,EnumType.valueOf(inNBT.getString("down")));
        instance.setDir(EnumDir.left,EnumType.valueOf(inNBT.getString("left")));
        instance.setDir(EnumDir.right,EnumType.valueOf(inNBT.getString("right")));
        instance.setCurrent(EnumType.input,EnumDir.valueOf(((NBTTagCompound) nbt).getString("current input")));
        instance.setCurrent(EnumType.output,EnumDir.valueOf(((NBTTagCompound) nbt).getString("current output")));
    }
}
