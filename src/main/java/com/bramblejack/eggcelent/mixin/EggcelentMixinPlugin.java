package com.bramblejack.eggcelent.mixin;

import net.minecraftforge.fml.loading.FMLLoader;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EggcelentMixinPlugin implements IMixinConfigPlugin {

    private final Map<String, Boolean> modCache = new HashMap<>();

    private boolean isLoaded(String modId) {
        return modCache.computeIfAbsent(modId, id -> {
            try {
                return FMLLoader.getLoadingModList().getModFileById(id) != null;
            } catch (Exception e) {
                return false;
            }
        });
    }

    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() { return null; }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.endsWith("IncubateGoalMysticHatchMixin")) {
            return isLoaded("nest_egg") && isLoaded("mysticsbiomes");
        }
        if (mixinClassName.endsWith("IncubateGoalVBHatchMixin")
                || mixinClassName.endsWith("IncubateGoalMixin")) {
            return isLoaded("nest_egg") && isLoaded("vanillabackport");
        }
        if (mixinClassName.endsWith("RainbowChickenNestMixin")) {
            return isLoaded("mysticsbiomes") && isLoaded("incubation");
        }
        if (mixinClassName.endsWith("VariantEggItemMixin")
                || mixinClassName.endsWith("ThrownEggVariantMixin")
                || mixinClassName.endsWith("ChickenEggDropMixin")) {
            return isLoaded("vanillabackport");
        }
        if (mixinClassName.endsWith("LayEggInNestGoalMixin")) {
            return isLoaded("vanillabackport") && isLoaded("incubation");
        }

        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() { return null; }

    @Override
    public void preApply(String targetClassName, org.objectweb.asm.tree.ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, org.objectweb.asm.tree.ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}