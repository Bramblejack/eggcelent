package com.bramblejack.eggcelent.init.items;

import com.bramblejack.eggcelent.EggcelentMod;
import com.bramblejack.eggcelent.init.blocks.CAMysticBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CAMysticItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EggcelentMod.MODID);

    public static RegistryObject<Item> ORANGE_CHICKEN_EGG_CRATE_ITEM;
    public static RegistryObject<Item> YELLOW_CHICKEN_EGG_CRATE_ITEM;
    public static RegistryObject<Item> PINK_CHICKEN_EGG_CRATE_ITEM;
    public static RegistryObject<Item> PURPLE_CHICKEN_EGG_CRATE_ITEM;
    public static RegistryObject<Item> LIME_CHICKEN_EGG_CRATE_ITEM;
    public static RegistryObject<Item> CYAN_CHICKEN_EGG_CRATE_ITEM;

    public static void init() {
        ORANGE_CHICKEN_EGG_CRATE_ITEM = ITEMS.register("orange_chicken_egg_crate", () -> new BlockItem(CAMysticBlocks.ORANGE_CHICKEN_EGG_CRATE.get(), new Item.Properties()));
        YELLOW_CHICKEN_EGG_CRATE_ITEM = ITEMS.register("yellow_chicken_egg_crate", () -> new BlockItem(CAMysticBlocks.YELLOW_CHICKEN_EGG_CRATE.get(), new Item.Properties()));
        PINK_CHICKEN_EGG_CRATE_ITEM = ITEMS.register("pink_chicken_egg_crate", () -> new BlockItem(CAMysticBlocks.PINK_CHICKEN_EGG_CRATE.get(), new Item.Properties()));
        PURPLE_CHICKEN_EGG_CRATE_ITEM = ITEMS.register("purple_chicken_egg_crate", () -> new BlockItem(CAMysticBlocks.PURPLE_CHICKEN_EGG_CRATE.get(), new Item.Properties()));
        LIME_CHICKEN_EGG_CRATE_ITEM = ITEMS.register("lime_chicken_egg_crate", () -> new BlockItem(CAMysticBlocks.LIME_CHICKEN_EGG_CRATE.get(), new Item.Properties()));
        CYAN_CHICKEN_EGG_CRATE_ITEM = ITEMS.register("cyan_chicken_egg_crate", () -> new BlockItem(CAMysticBlocks.CYAN_CHICKEN_EGG_CRATE.get(), new Item.Properties()));
    }
}