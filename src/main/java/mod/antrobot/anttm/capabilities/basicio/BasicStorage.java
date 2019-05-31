package mod.antrobot.anttm.capabilities.basicio;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class BasicStorage implements Capability.IStorage<IBasicIO> {
    @Override
    public NBTBase writeNBT(Capability<IBasicIO> capability, IBasicIO instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("input",instance.getInput().toString());
        nbt.setBoolean("output",instance.getOutput());
        return nbt;
    }

    @Override
    public void readNBT(Capability<IBasicIO> capability, IBasicIO instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound temp = (NBTTagCompound)nbt;
        instance.setInput(temp.getString("input"));
        instance.setOutput(temp.getBoolean("input"));
    }
}
