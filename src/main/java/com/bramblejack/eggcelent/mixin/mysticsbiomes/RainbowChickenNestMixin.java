package com.bramblejack.eggcelent.mixin.mysticsbiomes;

import com.mysticsbiomes.common.entity.RainbowChicken;
import com.mysticsbiomes.init.MysticItems;
import com.teamabnormals.blueprint.core.api.EggLayer;
import com.teamabnormals.incubation.common.entity.ai.goal.LayEggInNestGoal;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RainbowChicken.class)
public abstract class RainbowChickenNestMixin extends net.minecraft.world.entity.animal.Animal implements EggLayer {

    protected RainbowChickenNestMixin(EntityType<? extends net.minecraft.world.entity.animal.Animal> type, Level level) {
        super(type, level);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void eggcelent$addLayEggInNestGoal(CallbackInfo ci) {
        this.goalSelector.addGoal(3, new LayEggInNestGoal((RainbowChicken) (Object) this, 0.25));
    }

    @Override
    public int getEggTimer() {
        return ((RainbowChicken) (Object) this).eggTime;
    }

    @Override
    public void setEggTimer(int time) {
        ((RainbowChicken) (Object) this).eggTime = time;
    }

    @Override
    public boolean isBirdJockey() {
        return false;
    }

    @Override
    public Item getEggItem() {
        return switch (((RainbowChicken) (Object) this).getColor()) {
            case PINK -> MysticItems.PINK_EGG.get();
            case PURPLE -> MysticItems.PURPLE_EGG.get();
            case ORANGE -> MysticItems.ORANGE_EGG.get();
            case CYAN -> MysticItems.CYAN_EGG.get();
            case LIME -> MysticItems.LIME_EGG.get();
            default -> MysticItems.YELLOW_EGG.get();
        };
    }

    @Override
    public int getNextEggTime(RandomSource random) {
        return random.nextInt(6000) + 6000;
    }

    @Override
    public SoundEvent getEggLayingSound() {
        return SoundEvents.CHICKEN_EGG;
    }
}