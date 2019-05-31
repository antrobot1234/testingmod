package mod.antrobot.anttm.items;

import com.sun.istack.internal.Nullable;
import mod.antrobot.anttm.util.ModReference;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.client.resources.I18n;

import java.util.List;

public class ItemTooltip extends Item {
    public ItemTooltip(String tip){
        tipName = tip;}
    private static String tipName;
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
        tooltip.add(I18n.format(ModReference.ID+".tooltip."+tipName));
    }
}
