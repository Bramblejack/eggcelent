package com.bramblejack.eggcelent.mixin.incubation;

import com.bramblejack.eggcelent.util.ChickenVariantEggHelper;
import com.teamabnormals.blueprint.core.api.EggLayer;
import com.teamabnormals.incubation.common.block.EmptyNestBlock;
import com.teamabnormals.incubation.common.entity.ai.goal.LayEggInNestGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LayEggInNestGoal.class)
public abstract class LayEggInNestGoalMixin extends MoveToBlockGoal {

    @Shadow @Final private EggLayer eggLayer;

    protected LayEggInNestGoalMixin(PathfinderMob mob, double speed, int range) {
        super(mob, speed, range);
    }

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

    /**
     * Incubation accepts any EmptyNestBlock as a target, but EmptyNestBlock#getNest returns null when
     * that nest type has no BirdNestBlock registered for this egg (e.g. Rainbow Chicken eggs have hay
     * nests only, no twig nests), which then NPEs in tick. Only target nests that can take the egg.
     */
    @Inject(method = "isValidTarget", at = @At("RETURN"), cancellable = true)
    private void eggcelent$requireMatchingNest(LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ() && this.eggcelent$lacksNest(level.getBlockState(pos.above()))) {
            cir.setReturnValue(false);
        }
    }

    /**
     * The nest can change between targeting and laying; skip the tick instead of crashing.
     * The goal ends on the next canContinueToUse because isValidTarget is now false.
     */
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void eggcelent$skipTickWithoutMatchingNest(CallbackInfo ci) {
        if (this.eggcelent$lacksNest(this.mob.level().getBlockState(this.blockPos.above()))) {
            ci.cancel();
        }
    }

    private boolean eggcelent$lacksNest(BlockState state) {
        return state.getBlock() instanceof EmptyNestBlock nest
                && nest.getNest(ChickenVariantEggHelper.getVariantEgg(this.eggLayer)) == null;
    }
}
