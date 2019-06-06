package mod.antrobot.anttm.items;

import mod.antrobot.anttm.capabilities.standardio.EnumIO.EnumType;
import mod.antrobot.anttm.util.ModReference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class Inserter extends InserterBase{

    public Inserter(String tip) {
        super(tip, EnumType.nil, EnumType.output, EnumType.nil, EnumType.input,true);
    }

    //TODO FIX rendering

    @Override
    boolean isIPossible(ItemStack input, ItemStack worker) {
        return true;
    }

    @Override
    boolean isOPossible(ItemStack output, ItemStack worker) {
        return true;
    }

    @Override
    boolean customIORule(ItemStack input, ItemStack worker, ItemStack output) {
        return true;
    }

    @Override
    ItemStack transform(ItemStack input) {
        return input;
    }
}
