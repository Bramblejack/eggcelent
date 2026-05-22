package com.bramblejack.eggcelent;

import com.bramblejack.eggcelent.tab.EggcelentCreativeTab;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(EggcelentMod.MODID)
public class EggcelentMod {
    public static final String MODID = "eggcelent";
    public static final RegistryHelper EGGCELENT_HELPER = new RegistryHelper(MODID);

    public EggcelentMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EGGCELENT_HELPER.register(modEventBus);

        EggcelentCompat.init(modEventBus);
        EggcelentCreativeTab.init(modEventBus);
    }
}
