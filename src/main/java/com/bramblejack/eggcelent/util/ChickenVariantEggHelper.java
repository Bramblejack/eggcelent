package com.bramblejack.eggcelent.util;

import com.blackgear.vanillabackport.common.api.modules.mob_variant.VariantDataHolder;
import com.blackgear.vanillabackport.common.api.modules.mob_variant.VariantUtils;
import com.blackgear.vanillabackport.common.level.entities.mob.animal.chicken.ChickenVariant;
import com.blackgear.vanillabackport.common.level.entities.mob.animal.chicken.ChickenVariants;
import com.blackgear.vanillabackport.common.registries.items.ModItems;
import com.bramblejack.eggcelent.registry.ChickenVariantClassifier;
import com.teamabnormals.blueprint.core.api.EggLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Optional;

public final class ChickenVariantEggHelper {

    private ChickenVariantEggHelper() {}

    public static Item getVariantEgg(EggLayer eggLayer) {
        Item defaultEgg = eggLayer.getEggItem();
        if (!(eggLayer instanceof VariantDataHolder<?>)) return defaultEgg;

        @SuppressWarnings("unchecked")
        VariantDataHolder<ChickenVariant> holder = (VariantDataHolder<ChickenVariant>) eggLayer;
        Optional<ChickenVariant> variantOpt = holder.getVariantData();
        if (variantOpt.isEmpty()) return defaultEgg;

        ResourceLocation id = ResourceLocation.tryParse(
                VariantUtils.getID(ChickenVariants.REGISTRIES, variantOpt.get()));
        if (id == null) return defaultEgg;

        if (ChickenVariantClassifier.isColdVariant(id)) return ModItems.BLUE_EGG.get();
        if (ChickenVariantClassifier.isWarmVariant(id)) return ModItems.BROWN_EGG.get();

        return defaultEgg;
    }
}
