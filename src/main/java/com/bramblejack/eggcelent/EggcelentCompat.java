package com.bramblejack.eggcelent;

import com.bramblejack.eggcelent.init.blocks.CAMysticBlocks;
import com.bramblejack.eggcelent.init.blocks.CAVanillaBackportBlocks;
import com.bramblejack.eggcelent.init.items.CAMysticItems;
import com.bramblejack.eggcelent.init.items.CAVanillaBackportItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

public class EggcelentCompat {

    public static void init(IEventBus modEventBus) {
        if (ModList.get().isLoaded("mysticsbiomes") && ModList.get().isLoaded("incubation")) {
            // Force IncubationBlocks static initializer to run so its entries are
            // added to the DeferredRegister before we append ours.
            try { Class.forName("com.teamabnormals.incubation.core.registry.IncubationBlocks"); } catch (ClassNotFoundException ignored) {}
            CAMysticBlocks.BLOCKS.register(modEventBus);
            CAMysticBlocks.init();
            CAMysticItems.ITEMS.register(modEventBus);
            CAMysticItems.init();
        }
        if (ModList.get().isLoaded("vanillabackport") && ModList.get().isLoaded("incubation")) {
            try { Class.forName("com.teamabnormals.incubation.core.registry.IncubationBlocks"); } catch (ClassNotFoundException ignored) {}
            CAVanillaBackportBlocks.BLOCKS.register(modEventBus);
            CAVanillaBackportBlocks.init();
            CAVanillaBackportItems.ITEMS.register(modEventBus);
            CAVanillaBackportItems.init();
        }
    }
}
