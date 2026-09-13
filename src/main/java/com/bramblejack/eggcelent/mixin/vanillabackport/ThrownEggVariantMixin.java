package com.bramblejack.eggcelent.mixin.vanillabackport;

import com.blackgear.vanillabackport.common.api.modules.mob_variant.VariantDataHolder;
import com.blackgear.vanillabackport.common.level.entities.mob.animal.chicken.ChickenVariant;
import com.blackgear.vanillabackport.common.level.entities.mob.animal.chicken.ChickenVariants;
import com.bramblejack.eggcelent.registry.ChickenVariantClassifier;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

/**
 * When a regular egg is thrown, sets a random temperate variant on the ThrownEgg
 * by wrapping the addFreshEntity call. VanillaBackport then copies the variant to
 * the hatched chicken on impact.
 */
@Mixin(EggItem.class)
public abstract class ThrownEggVariantMixin {

    @WrapOperation(
        method = "use",
        at = @At(value = "INVOKE",
                 target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z")
    )
    private boolean eggcelent$wrapAddFreshEntity(Level level, Entity entity,
                                                        Operation<Boolean> original,
                                                        Level levelArg, Player player, InteractionHand hand) {
        if (!level.isClientSide && entity instanceof ThrownEgg thrownEgg) {
            Optional<ResourceLocation> randomId = ChickenVariantClassifier.randomTemperateVariant(level.random);
            randomId.ifPresent(id -> {
                ChickenVariant chosen = ChickenVariants.REGISTRIES.get(id);
                if (chosen != null) {
                    VariantDataHolder.<ChickenVariant>getHolder(thrownEgg)
                            .ifPresent(holder -> holder.setVariantData(chosen));
                }
            });
        }

        return original.call(level, entity);
    }
}
