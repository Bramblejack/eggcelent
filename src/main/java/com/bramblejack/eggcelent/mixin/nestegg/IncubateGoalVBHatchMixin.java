package com.bramblejack.eggcelent.mixin.nestegg;

import com.blackgear.vanillabackport.common.api.variant.VariantDataHolder;
import com.blackgear.vanillabackport.common.api.variant.VariantUtils;
import com.blackgear.vanillabackport.common.level.entities.animal.ChickenVariant;
import com.blackgear.vanillabackport.core.registries.ModBuiltinRegistries;
import com.bramblejack.eggcelent.registry.ChickenVariantClassifier;
import com.davigj.nest_egg.common.entity.ai.goal.IncubateGoal;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.teamabnormals.blueprint.core.api.EggLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.Optional;

@Mixin(value = IncubateGoal.class, remap = false)
public abstract class IncubateGoalVBHatchMixin {

    @Shadow protected EggLayer eggLayer;

    @WrapOperation(
        method = "onReachedTarget",
        at = @At(value = "INVOKE", target = "Lcom/davigj/nest_egg/common/entity/ai/goal/IncubateGoal;hatchEgg(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;)V"),
        remap = false
    )
    private void eggcelent$hatchWithVBVariant(IncubateGoal goal, BlockState state, Level level, Operation<Void> original) {
        Optional<ResourceLocation> pickedVariant = Optional.empty();
        if (eggLayer instanceof VariantDataHolder<?>) {
            @SuppressWarnings("unchecked")
            VariantDataHolder<ChickenVariant> parentHolder = (VariantDataHolder<ChickenVariant>) eggLayer;
            Optional<ChickenVariant> parentVariant = parentHolder.getVariantData();

            if (parentVariant.isPresent()) {
                String variantIdStr = VariantUtils.getID(ModBuiltinRegistries.CHICKEN_VARIANTS, parentVariant.get());
                ResourceLocation variantId = ResourceLocation.tryParse(variantIdStr);

                if (variantId != null) {
                    boolean isWarm = ChickenVariantClassifier.isWarmVariant(variantId);
                    boolean isCold = ChickenVariantClassifier.isColdVariant(variantId);

                    if (isWarm || isCold) {
                        pickedVariant = isWarm
                                ? ChickenVariantClassifier.randomWarmVariant(level.random)
                                : ChickenVariantClassifier.randomColdVariant(level.random);
                    }
                }
            }
        }

        original.call(goal, state, level);

        if (pickedVariant.isEmpty()) return;

        final ResourceLocation variantId = pickedVariant.get();
        final Animal mob = (Animal) eggLayer;
        List<Animal> candidates = level.getEntitiesOfClass(Animal.class,
                mob.getBoundingBox().inflate(1.0),
                e -> e.isBaby() && e.getType() == mob.getType() && e instanceof VariantDataHolder<?>
        );
        candidates.forEach(chick -> {
            @SuppressWarnings("unchecked")
            VariantDataHolder<ChickenVariant> chickHolder = (VariantDataHolder<ChickenVariant>) chick;
            Optional<ChickenVariant> resolved = VariantUtils.getOrDefault(ModBuiltinRegistries.CHICKEN_VARIANTS, variantId.toString());
            resolved.ifPresent(chickHolder::setVariantData);
        });
    }
}
