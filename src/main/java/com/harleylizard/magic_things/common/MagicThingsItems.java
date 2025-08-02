package com.harleylizard.magic_things.common;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class MagicThingsItems {
    public static final Item FOULED_LOG = blockItem(MagicThingsBlocks.FOULED_LOG);
    public static final Item FOULED_LOG_ROOT = blockItem(MagicThingsBlocks.FOULED_LOG_ROOT);
    public static final Item FOULED_WOOD = blockItem(MagicThingsBlocks.FOULED_WOOD);
    public static final Item FOULED_SAPLING = blockItem(MagicThingsBlocks.FOULED_SAPLING);
    public static final Item FOULED_GROWTH = blockItem(MagicThingsBlocks.FOULED_GROWTH);
    public static final Item WILTING_GRASS = blockItem(MagicThingsBlocks.WILTING_GRASS);

    public static void register() {
        register("fouled_log", FOULED_LOG);
        register("fouled_log_root", FOULED_LOG_ROOT);
        register("fouled_wood", FOULED_WOOD);
        register("fouled_sapling", FOULED_SAPLING);
        register("fouled_growth", FOULED_GROWTH);
        register("wilting_grass", WILTING_GRASS);
    }

    public static void register(String name, Item item) {
        Registry.register(BuiltInRegistries.ITEM, MagicThings.resourceLocation(name), item);
    }

    public static Item blockItem(Block block) {
        return new BlockItem(block, new Item.Properties());
    }

}
