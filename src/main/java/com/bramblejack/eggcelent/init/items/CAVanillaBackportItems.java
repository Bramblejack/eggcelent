package com.bramblejack.eggcelent.init.items;


import com.bramblejack.eggcelent.EggcelentMod;
import com.bramblejack.eggcelent.init.blocks.CAVanillaBackportBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CAVanillaBackportItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EggcelentMod.MODID);

    public static RegistryObject<Item> BROWN_CHICKEN_EGG_CRATE_ITEM;
    public static RegistryObject<Item> BLUE_CHICKEN_EGG_CRATE_ITEM;

    public static void init() {
        BROWN_CHICKEN_EGG_CRATE_ITEM = ITEMS.register("brown_chicken_egg_crate", () -> new BlockItem(CAVanillaBackportBlocks.BROWN_CHICKEN_EGG_CRATE.get(), new Item.Properties()));
        BLUE_CHICKEN_EGG_CRATE_ITEM = ITEMS.register("blue_chicken_egg_crate", () -> new BlockItem(CAVanillaBackportBlocks.BLUE_CHICKEN_EGG_CRATE.get(), new Item.Properties()));
    }
}