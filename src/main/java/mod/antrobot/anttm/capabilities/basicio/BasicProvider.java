package mod.antrobot.anttm.capabilities.basicio;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class BasicProvider {
    @CapabilityInject(IBasicIO.class)
    private static Capability<IBasicIO> IO_CAPABILITY = null;
    public static ICapabilitySerializable<NBTTagCompound> provide(String input, boolean output){

        IBasicIO instance = IO_CAPABILITY.getDefaultInstance();
        instance.setInput(input);
        instance.setOutput(output);
        return new ICapabilitySerializable<NBTTagCompound>() {
            @Override
            public NBTTagCompound serializeNBT(){
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setString("input",instance.getInput().toString());
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
