package com.bramblejack.eggcelent.init.blocks;

import com.blackgear.vanillabackport.common.registries.ModItems;

import com.bramblejack.eggcelent.EggcelentMod;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.incubation.common.block.BirdNestBlock;
import com.teamabnormals.incubation.common.block.EmptyNestBlock;
import com.teamabnormals.incubation.core.Incubation;
import com.teamabnormals.incubation.core.registry.IncubationBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CAVanillaBackportBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, EggcelentMod.MODID);
    public static final BlockSubRegistryHelper HELPER =
            Incubation.REGISTRY_HELPER.getBlockSubHelper();

    public static RegistryObject<Block> HAY_COLD_CHICKEN_NEST;
    public static RegistryObject<Block> TWIG_COLD_CHICKEN_NEST;
    public static RegistryObject<Block> HAY_WARM_CHICKEN_NEST;
    public static RegistryObject<Block> TWIG_WARM_CHICKEN_NEST;

    public static RegistryObject<Block> BROWN_CHICKEN_EGG_CRATE;
    public static RegistryObject<Block> BLUE_CHICKEN_EGG_CRATE;

    public static void init() {
        HAY_COLD_CHICKEN_NEST = HELPER.createBlockNoItem("hay_cold_chicken_nest",
                () -> new BirdNestBlock(ModItems.BLUE_EGG::get, (EmptyNestBlock) IncubationBlocks.HAY_NEST.get(), IncubationBlocks.IncubationProperties.HAY_NEST));
        TWIG_COLD_CHICKEN_NEST = HELPER.createBlockNoItem("twig_cold_chicken_nest",
                () -> new BirdNestBlock(ModItems.BLUE_EGG::get, (EmptyNestBlock) IncubationBlocks.TWIG_NEST.get(), IncubationBlocks.IncubationProperties.TWIG_NEST));
        HAY_WARM_CHICKEN_NEST = HELPER.createBlockNoItem("hay_warm_chicken_nest",
                () -> new BirdNestBlock(ModItems.BROWN_EGG::get, (EmptyNestBlock) IncubationBlocks.HAY_NEST.get(), IncubationBlocks.IncubationProperties.HAY_NEST));
        TWIG_WARM_CHICKEN_NEST = HELPER.createBlockNoItem("twig_warm_chicken_nest",
                () -> new BirdNestBlock(ModItems.BROWN_EGG::get, (EmptyNestBlock) IncubationBlocks.TWIG_NEST.get(), IncubationBlocks.IncubationProperties.TWIG_NEST));

        BROWN_CHICKEN_EGG_CRATE = BLOCKS.register("brown_chicken_egg_crate", () -> new Block(BlockBehaviour.Properties.copy(IncubationBlocks.CHICKEN_EGG_CRATE.get()).mapColor(MapColor.TERRACOTTA_BROWN)));
        BLUE_CHICKEN_EGG_CRATE = BLOCKS.register("blue_chicken_egg_crate", () -> new Block(BlockBehaviour.Properties.copy(IncubationBlocks.CHICKEN_EGG_CRATE.get()).mapColor(MapColor.TERRACOTTA_WHITE)));
    }
}