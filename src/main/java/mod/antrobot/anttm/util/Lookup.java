package mod.antrobot.anttm.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Optional;

public class Lookup {
    public static Optional<Item> lookupItem(String name) {
        ResourceLocation loc = new ResourceLocation(name);
        return Optional.ofNullable(ForgeRegistries.ITEMS.getValue(loc));
    }
    public static Optional<Block> lookupBlock(String name) {
        ResourceLocation loc = new ResourceLocation(name);
        return Optional.ofNullable(ForgeRegistries.BLOCKS.getValue(loc));
    }
}
