package com.bramblejack.eggcelent.tab;

import com.bramblejack.eggcelent.init.items.CAMysticItems;
import com.bramblejack.eggcelent.init.items.CAVanillaBackportItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

public class EggcelentCreativeTab {

    public static void init(IEventBus modEventBus) {
        if (ModList.get().isLoaded("mysticsbiomes") && ModList.get().isLoaded("incubation")) {
            modEventBus.addListener(EggcelentCreativeTab::addMysticItems);
        }
        if (ModList.get().isLoaded("vanillabackport") && ModList.get().isLoaded("incubation")) {
            modEventBus.addListener(EggcelentCreativeTab::addVanillaBackportItems);
        }
    }

    private static void addMysticItems(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != CreativeModeTabs.NATURAL_BLOCKS) return;

        event.accept(CAMysticItems.ORANGE_CHICKEN_EGG_CRATE_ITEM);
        event.accept(CAMysticItems.YELLOW_CHICKEN_EGG_CRATE_ITEM);
        event.accept(CAMysticItems.PINK_CHICKEN_EGG_CRATE_ITEM);
        event.accept(CAMysticItems.PURPLE_CHICKEN_EGG_CRATE_ITEM);
        event.accept(CAMysticItems.LIME_CHICKEN_EGG_CRATE_ITEM);
        event.accept(CAMysticItems.CYAN_CHICKEN_EGG_CRATE_ITEM);
    }

    private static void addVanillaBackportItems(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != CreativeModeTabs.NATURAL_BLOCKS) return;

        event.accept(CAVanillaBackportItems.BROWN_CHICKEN_EGG_CRATE_ITEM);
        event.accept(CAVanillaBackportItems.BLUE_CHICKEN_EGG_CRATE_ITEM);
    }
}
