package com.bramblejack.eggcelent.mixin.vanillabackport;

import com.blackgear.vanillabackport.common.api.modules.mob_variant.VariantDataHolder;
import com.blackgear.vanillabackport.common.api.modules.mob_variant.VariantUtils;
import com.blackgear.vanillabackport.common.level.entities.mob.animal.chicken.ChickenVariant;
import com.blackgear.vanillabackport.common.level.entities.mob.animal.chicken.ChickenVariants;
import com.blackgear.vanillabackport.common.registries.items.ModItems;
import com.bramblejack.eggcelent.registry.ChickenVariantClassifier;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

/**
 * Wraps the spawnAtLocation(ItemLike) call inside Chicken.aiStep.
 * VanillaBackport's @ModifyArg (vb$modifyEggDrop) runs first and substitutes
 * the item for its 3 known variants. Our @WrapOperation then runs on the
 * result, replacing it for any variant classified by biome tag.
 */
@Mixin(Chicken.class)
public abstract class ChickenEggDropMixin {

    @WrapOperation(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/animal/Chicken;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"
            )
    )
    private ItemEntity eggcelent$wrapSpawnEgg(
            Chicken instance,
            ItemLike item,
            Operation<ItemEntity> original) {

        @SuppressWarnings("unchecked")
        Optional<ChickenVariant> variantOpt =
                ((VariantDataHolder<ChickenVariant>) (Object) this).getVariantData();

        if (variantOpt.isPresent()) {
            String variantIdStr = VariantUtils.getID(ChickenVariants.REGISTRIES, variantOpt.get());
            ResourceLocation variantId = ResourceLocation.tryParse(variantIdStr);

            if (variantId != null) {
                if (ChickenVariantClassifier.isColdVariant(variantId))
                    return original.call(instance, (ItemLike) ModItems.BLUE_EGG.get());
                if (ChickenVariantClassifier.isWarmVariant(variantId))
                    return original.call(instance, (ItemLike) ModItems.BROWN_EGG.get());
                // temperate — explicit white egg drop
                return original.call(instance, Items.EGG);
            }
        }
        return original.call(instance, item);
    }
}
