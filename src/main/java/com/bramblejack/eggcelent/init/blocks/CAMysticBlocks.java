package com.bramblejack.eggcelent.init.blocks;

import com.bramblejack.eggcelent.EggcelentMod;
import com.mysticsbiomes.init.MysticItems;
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

public class CAMysticBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, EggcelentMod.MODID);
    public static final BlockSubRegistryHelper HELPER =
            Incubation.REGISTRY_HELPER.getBlockSubHelper();

    public static RegistryObject<Block> HAY_YELLOW_CHICKEN_NEST;
    public static RegistryObject<Block> HAY_PINK_CHICKEN_NEST;
    public static RegistryObject<Block> HAY_ORANGE_CHICKEN_NEST;
    public static RegistryObject<Block> HAY_PURPLE_CHICKEN_NEST;
    public static RegistryObject<Block> HAY_CYAN_CHICKEN_NEST;
    public static RegistryObject<Block> HAY_LIME_CHICKEN_NEST;

    public static RegistryObject<Block> CYAN_CHICKEN_EGG_CRATE;
    public static RegistryObject<Block> YELLOW_CHICKEN_EGG_CRATE;
    public static RegistryObject<Block> ORANGE_CHICKEN_EGG_CRATE;
    public static RegistryObject<Block> PINK_CHICKEN_EGG_CRATE;
    public static RegistryObject<Block> PURPLE_CHICKEN_EGG_CRATE;
    public static RegistryObject<Block> LIME_CHICKEN_EGG_CRATE;

    public static void init() {
        HAY_YELLOW_CHICKEN_NEST = HELPER.createBlockNoItem("hay_yellow_chicken_nest",
                () -> new BirdNestBlock(MysticItems.YELLOW_EGG::get, (EmptyNestBlock) IncubationBlocks.HAY_NEST.get(), IncubationBlocks.IncubationProperties.HAY_NEST));
        HAY_PINK_CHICKEN_NEST = HELPER.createBlockNoItem("hay_pink_chicken_nest",
                () -> new BirdNestBlock(MysticItems.PINK_EGG::get, (EmptyNestBlock) IncubationBlocks.HAY_NEST.get(), IncubationBlocks.IncubationProperties.HAY_NEST));
        HAY_ORANGE_CHICKEN_NEST = HELPER.createBlockNoItem("hay_orange_chicken_nest",
                () -> new BirdNestBlock(MysticItems.ORANGE_EGG::get, (EmptyNestBlock) IncubationBlocks.HAY_NEST.get(), IncubationBlocks.IncubationProperties.HAY_NEST));
        HAY_PURPLE_CHICKEN_NEST = HELPER.createBlockNoItem("hay_purple_chicken_nest",
                () -> new BirdNestBlock(MysticItems.PURPLE_EGG::get, (EmptyNestBlock) IncubationBlocks.HAY_NEST.get(), IncubationBlocks.IncubationProperties.HAY_NEST));
        HAY_CYAN_CHICKEN_NEST = HELPER.createBlockNoItem("hay_cyan_chicken_nest",
                () -> new BirdNestBlock(MysticItems.CYAN_EGG::get, (EmptyNestBlock) IncubationBlocks.HAY_NEST.get(), IncubationBlocks.IncubationProperties.HAY_NEST));
        HAY_LIME_CHICKEN_NEST = HELPER.createBlockNoItem("hay_lime_chicken_nest",
                () -> new BirdNestBlock(MysticItems.LIME_EGG::get, (EmptyNestBlock) IncubationBlocks.HAY_NEST.get(), IncubationBlocks.IncubationProperties.HAY_NEST));

        CYAN_CHICKEN_EGG_CRATE = BLOCKS.register("cyan_chicken_egg_crate",
                () -> new Block(BlockBehaviour.Properties.copy(IncubationBlocks.CHICKEN_EGG_CRATE.get()).mapColor(MapColor.COLOR_CYAN)));
        YELLOW_CHICKEN_EGG_CRATE = BLOCKS.register("yellow_chicken_egg_crate",
                () -> new Block(BlockBehaviour.Properties.copy(IncubationBlocks.CHICKEN_EGG_CRATE.get()).mapColor(MapColor.COLOR_YELLOW)));
        ORANGE_CHICKEN_EGG_CRATE = BLOCKS.register("orange_chicken_egg_crate",
                () -> new Block(BlockBehaviour.Properties.copy(IncubationBlocks.CHICKEN_EGG_CRATE.get()).mapColor(MapColor.COLOR_ORANGE)));
        PINK_CHICKEN_EGG_CRATE = BLOCKS.register("pink_chicken_egg_crate",
                () -> new Block(BlockBehaviour.Properties.copy(IncubationBlocks.CHICKEN_EGG_CRATE.get()).mapColor(MapColor.COLOR_PINK)));
        PURPLE_CHICKEN_EGG_CRATE = BLOCKS.register("purple_chicken_egg_crate",
                () -> new Block(BlockBehaviour.Properties.copy(IncubationBlocks.CHICKEN_EGG_CRATE.get()).mapColor(MapColor.COLOR_PURPLE)));
        LIME_CHICKEN_EGG_CRATE = BLOCKS.register("lime_chicken_egg_crate",
                () -> new Block(BlockBehaviour.Properties.copy(IncubationBlocks.CHICKEN_EGG_CRATE.get()).mapColor(MapColor.COLOR_LIGHT_GREEN)));
    }
}