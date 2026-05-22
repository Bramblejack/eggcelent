package com.bramblejack.eggcelent.mixin.nestegg;

import com.bramblejack.eggcelent.util.ChickenVariantEggHelper;
import com.davigj.nest_egg.common.entity.ai.goal.IncubateGoal;
import com.teamabnormals.blueprint.core.api.EggLayer;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(IncubateGoal.class)
public abstract class IncubateGoalMixin {

    @Redirect(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lcom/teamabnormals/blueprint/core/api/EggLayer;getEggItem()Lnet/minecraft/world/item/Item;")
    )
    private Item eggcelent$redirectGetEggItemTick(EggLayer eggLayer) {
        return ChickenVariantEggHelper.getVariantEgg(eggLayer);
    }

    @Redirect(
            method = "isValidTarget",
            at = @At(value = "INVOKE", target = "Lcom/teamabnormals/blueprint/core/api/EggLayer;getEggItem()Lnet/minecraft/world/item/Item;")
    )
    private Item eggcelent$redirectGetEggItemValidTarget(EggLayer eggLayer) {
        return ChickenVariantEggHelper.getVariantEgg(eggLayer);
    }
}