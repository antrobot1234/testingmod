package mod.antrobot.anttm;
import mod.antrobot.anttm.proxy.CommonProxy;
import mod.antrobot.anttm.util.ModReference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;

@Mod(modid = ModReference.ID,name = ModReference.NAME,version = ModReference.VERSION)
public class Main {

    @Mod.Instance
    public static Main instance;
    @SidedProxy(clientSide = ModReference.CLIENT, serverSide = ModReference.COMMON)
    public static CommonProxy proxy;
}
