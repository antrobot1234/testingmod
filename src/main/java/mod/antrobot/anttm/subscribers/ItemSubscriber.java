package mod.antrobot.anttm.subscribers;


import mod.antrobot.anttm.items.Inserter;
import mod.antrobot.anttm.items.ItemTooltip;
import mod.antrobot.anttm.util.ModItemList;
import mod.antrobot.anttm.util.ModReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Map;

import static mod.antrobot.anttm.subscribers.SubscriberTools.setupKey;
import static mod.antrobot.anttm.subscribers.SubscriberTools.setupRegistry;

@Mod.EventBusSubscriber(modid = ModReference.ID)
public class ItemSubscriber {
    @SubscribeEvent
    public static void onRegisterItemsEvent(final RegistryEvent.Register<Item> event) {
        //add mod items here
        ModItemList.ITEMS.put(new Inserter("inserter"),"inserter");
        ModItemList.ITEMS.put(new ItemTooltip("extender"),"extender");

        final IForgeRegistry<Item> registry = event.getRegistry();
        for(Map.Entry<Item,String> entry:ModItemList.ITEMS.entrySet()){
            registry.register(setupRegistry(entry.getKey(),entry.getValue()));
        }

        ForgeRegistries.BLOCKS.getValuesCollection().stream()
                .filter(block -> block.getRegistryName().getNamespace().equals(ModReference.ID))
                .filter(SubscriberTools::hasItemBlock)
                .forEach(block -> {
                    registry.register(setupKey(new ItemBlock(block), block.getRegistryName()));
                });
    }

}
