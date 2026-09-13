package com.bramblejack.eggcelent.registry;

import com.blackgear.vanillabackport.common.api.modules.mob_variant.spawn.SpawnCondition;
import com.blackgear.vanillabackport.common.api.modules.mob_variant.spawn.check.BiomeCheck;
import com.blackgear.vanillabackport.common.api.modules.mob_variant.spawn.check.raw.RawBiomeCheck;
import com.blackgear.vanillabackport.common.level.entities.mob.animal.chicken.ChickenVariant;
import com.blackgear.vanillabackport.common.level.entities.mob.animal.chicken.ChickenVariants;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Classifies VanillaBackport chicken variants into warm/cold/temperate groups
 * by inspecting the biome tags declared in their spawn_conditions, instead of
 * relying on a separate Eggcelent datapack registry.
 *
 * VB uses two condition types depending on how the variant was registered:
 *   RawBiomeCheck — used by hardcoded built-in variants (WARM, COLD); holds TagKey<Biome> directly
 *   BiomeCheck    — used by datapack-defined variants; holds HolderSet<Biome> (may be a named tag)
 *
 * Classification rules:
 *   warm      — any selector's biome tag is #minecraft:spawns_warm_variant_farm_animals
 *   cold      — any selector's biome tag is #minecraft:spawns_cold_variant_farm_animals
 *   temperate — everything else (fallback / no biome condition)
 */
public final class ChickenVariantClassifier {

    private static final ResourceLocation WARM_TAG =
            new ResourceLocation("minecraft", "spawns_warm_variant_farm_animals");
    private static final ResourceLocation COLD_TAG =
            new ResourceLocation("minecraft", "spawns_cold_variant_farm_animals");

    private ChickenVariantClassifier() {}

    public enum Group { WARM, COLD, TEMPERATE }

    // -------------------------------------------------------------------------
    // Core classification
    // -------------------------------------------------------------------------

    public static Group classify(ChickenVariant variant) {
        for (var selector : variant.spawnConditions().selectors()) {
            Optional<SpawnCondition> condOpt = selector.condition();
            if (condOpt.isEmpty()) continue;

            ResourceLocation tagLoc = getBiomeTagLocation(condOpt.get());
            if (tagLoc == null) continue;

            if (tagLoc.equals(WARM_TAG)) return Group.WARM;
            if (tagLoc.equals(COLD_TAG)) return Group.COLD;
        }
        return Group.TEMPERATE;
    }

    /**
     * Extracts the biome tag ResourceLocation from either condition type VB uses.
     * Returns null if the condition is not biome-based or the tag cannot be resolved.
     */
    private static ResourceLocation getBiomeTagLocation(SpawnCondition condition) {
        // Built-in variants (WARM, COLD) use RawBiomeCheck with a TagKey directly
        if (condition instanceof RawBiomeCheck rawCheck) {
            return rawCheck.requiredBiomes().location();
        }
        // Datapack-defined variants use BiomeCheck with a HolderSet
        if (condition instanceof BiomeCheck biomeCheck) {
            HolderSet<Biome> biomes = biomeCheck.requiredBiomes();
            if (biomes instanceof HolderSet.Named<Biome> named) {
                return named.key().location();
            }
        }
        return null;
    }

    // -------------------------------------------------------------------------
    // Convenience checkers
    // -------------------------------------------------------------------------

    public static boolean isWarmVariant(ResourceLocation variantId) {
        ChickenVariant variant = ChickenVariants.REGISTRIES.get(variantId);
        return variant != null && classify(variant) == Group.WARM;
    }

    public static boolean isColdVariant(ResourceLocation variantId) {
        ChickenVariant variant = ChickenVariants.REGISTRIES.get(variantId);
        return variant != null && classify(variant) == Group.COLD;
    }

    // -------------------------------------------------------------------------
    // Random pool selectors
    // -------------------------------------------------------------------------

    public static Optional<ResourceLocation> randomWarmVariant(RandomSource random) {
        return randomFromGroup(random, Group.WARM);
    }

    public static Optional<ResourceLocation> randomColdVariant(RandomSource random) {
        return randomFromGroup(random, Group.COLD);
    }

    public static Optional<ResourceLocation> randomTemperateVariant(RandomSource random) {
        return randomFromGroup(random, Group.TEMPERATE);
    }

    private static Optional<ResourceLocation> randomFromGroup(RandomSource random, Group group) {
        List<ResourceLocation> pool = new ArrayList<>();
        ChickenVariants.REGISTRIES.entries().forEach((id, variant) -> {
            if (classify(variant) == group) {
                pool.add(id);
            }
        });
        if (pool.isEmpty()) return Optional.empty();
        return Optional.of(pool.get(random.nextInt(pool.size())));
    }
}

