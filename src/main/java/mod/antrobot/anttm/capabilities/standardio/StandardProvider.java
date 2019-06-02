package mod.antrobot.anttm.capabilities.standardio;
import mod.antrobot.anttm.capabilities.standardio.EnumIO.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class StandardProvider {
    @CapabilityInject(IStandardIO.class)
    static Capability<IStandardIO> IO_CAPABILITY = null;

    public static ICapabilitySerializable<NBTTagCompound> provide(EnumType up, EnumType right, EnumType down, EnumType left){
        IStandardIO instance = IO_CAPABILITY.getDefaultInstance();
        if(instance == null)return null;
        instance.initMap(up,right,down,left);
        return new ICapabilitySerializable<NBTTagCompound>() {
            @Override
            public NBTTagCompound serializeNBT(){
                return (NBTTagCompound)IO_CAPABILITY.writeNBT(instance,null);
            }
            @Override
            public void deserializeNBT(NBTTagCompound nbt){
                IO_CAPABILITY.readNBT(instance,null,nbt);
            }

            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                return capability == IO_CAPABILITY;
            }

            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                if(capability == IO_CAPABILITY) return IO_CAPABILITY.cast(instance);
                return null;
            }
        };
    }
}
