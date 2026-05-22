package com.bramblejack.eggcelent.mixin.vanillabackport;

import com.blackgear.platform.core.BuiltInCoreRegistry;
import com.blackgear.vanillabackport.common.level.entities.animal.ChickenVariant;
import com.blackgear.vanillabackport.common.level.entities.animal.ChickenVariants;
import com.blackgear.vanillabackport.common.level.items.VariantEggItem;
import com.blackgear.vanillabackport.core.registries.ModBuiltinRegistries;
import com.bramblejack.eggcelent.registry.ChickenVariantClassifier;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(value = VariantEggItem.class)
public abstract class VariantEggItemMixin {

    /**
     * Wrap VariantUtils.getDefault() inside use() to pick a random variant
     * from the classifier instead of the single hardcoded one.
     */
    @WrapOperation(
        method = "use",
        at = @At(value = "INVOKE",
                 target = "Lcom/blackgear/vanillabackport/common/api/variant/VariantUtils;getDefault(Lcom/blackgear/platform/core/BuiltInCoreRegistry;Lnet/minecraft/resources/ResourceKey;)Ljava/lang/Object;")
    )
    private Object eggcelent$wrapGetDefault(BuiltInCoreRegistry<ChickenVariant> registry,
                                                   ResourceKey<ChickenVariant> defaultKey,
                                                   Operation<ChickenVariant> original,
                                                   Level level, Player player, InteractionHand hand) {
        ChickenVariant defaultVariant = original.call(registry, defaultKey);

        boolean isCold = ChickenVariants.COLD.equals(defaultKey);
        boolean isWarm = ChickenVariants.WARM.equals(defaultKey);
        if (!isCold && !isWarm) return defaultVariant;

        if (level.isClientSide) return defaultVariant;

        Optional<ResourceLocation> randomId = isCold
                ? ChickenVariantClassifier.randomColdVariant(level.random)
                : ChickenVariantClassifier.randomWarmVariant(level.random);

        return randomId
                .map(id -> ModBuiltinRegistries.CHICKEN_VARIANTS.get(id))
                .filter(v -> v != null)
                .orElse(defaultVariant);
    }
}
