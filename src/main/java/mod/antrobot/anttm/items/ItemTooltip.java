package mod.antrobot.anttm.items;


import mod.antrobot.anttm.util.ModReference;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemTooltip extends Item {
    public ItemTooltip(String tip){
        tipName = tip;}
    private String tipName;
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn){
        tooltip.add(I18n.format(ModReference.ID+".tooltip."+tipName));
    }
}
