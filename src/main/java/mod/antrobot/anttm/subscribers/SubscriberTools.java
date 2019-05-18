package mod.antrobot.anttm.subscribers;

import mod.antrobot.anttm.util.ModReference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;



public class SubscriberTools {
    public static boolean hasItemBlock(Block block){
        return true;
    }
    public static <T extends IForgeRegistryEntry> T setupRegistry( final T entry,  final String name){
        return setupKey(entry, new ResourceLocation(ModReference.ID, name));
    }
    public static <T extends IForgeRegistryEntry> T setupKey( final T entry,  final ResourceLocation registryName){

        entry.setRegistryName(registryName);
        if (entry instanceof Block) {
            ((Block) entry).setTranslationKey(registryName.getNamespace() + "." + registryName.getPath());
        }
        if (entry instanceof Item) {
            ((Item) entry).setTranslationKey(registryName.getNamespace() + "." + registryName.getPath());
        }
        return entry;
    }
}
