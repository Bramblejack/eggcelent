package com.bramblejack.eggcelent.mixin.nestegg;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mysticsbiomes.common.entity.RainbowChicken;
import com.mysticsbiomes.init.MysticItems;
import com.davigj.nest_egg.common.entity.ai.goal.IncubateGoal;
import com.teamabnormals.blueprint.core.api.EggLayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = IncubateGoal.class, remap = false)
public abstract class IncubateGoalMysticHatchMixin {

    @Shadow protected EggLayer eggLayer;

    @WrapOperation(
        method = "onReachedTarget",
        at = @At(value = "INVOKE", target = "Lcom/davigj/nest_egg/common/entity/ai/goal/IncubateGoal;hatchEgg(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;)V"),
        remap = false
    )
    private void eggcelent$hatchWithMysticColor(IncubateGoal goal, BlockState state, Level level, Operation<Void> original) {
        RainbowChicken.Color rainbowColor = null;
        if (eggLayer instanceof RainbowChicken) {
            rainbowColor = colorFromEgg(eggLayer.getEggItem());
        }

        original.call(goal, state, level);

        if (rainbowColor == null) return;

        final RainbowChicken.Color finalColor = rainbowColor;
        final var mob = (net.minecraft.world.entity.animal.Animal) eggLayer;
        level.getEntitiesOfClass(RainbowChicken.class,
                mob.getBoundingBox().inflate(1.0),
                e -> e.isBaby()
        ).forEach(chick -> chick.setColor(finalColor));
    }

    private static RainbowChicken.Color colorFromEgg(Item egg) {
        if (egg == MysticItems.PINK_EGG.get())   return RainbowChicken.Color.PINK;
        if (egg == MysticItems.PURPLE_EGG.get()) return RainbowChicken.Color.PURPLE;
        if (egg == MysticItems.ORANGE_EGG.get()) return RainbowChicken.Color.ORANGE;
        if (egg == MysticItems.CYAN_EGG.get())   return RainbowChicken.Color.CYAN;
        if (egg == MysticItems.LIME_EGG.get())   return RainbowChicken.Color.LIME;
        return RainbowChicken.Color.YELLOW;
    }
}
