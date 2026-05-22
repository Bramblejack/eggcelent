package com.bramblejack.eggcelent.mixin.mysticsbiomes;

import com.davigj.nest_egg.common.entity.ai.goal.IncubateGoal;
import com.mysticsbiomes.common.entity.RainbowChicken;
import com.teamabnormals.blueprint.core.api.EggLayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RainbowChicken.class)
public abstract class RainbowChickenIncubateGoalMixin extends net.minecraft.world.entity.animal.Animal {

    protected RainbowChickenIncubateGoalMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void eggcelent$addIncubateGoal(CallbackInfo ci) {
        this.goalSelector.addGoal(2, new IncubateGoal((EggLayer) this, 0.25));
    }
}