package com.harleylizard.magic_things.common;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class MagicThingsItems {

    public static void register() {

    }

    public static void register(String name, Item item) {

    }

    public static Item blockItem(Block block) {
        return new BlockItem(block, new Item.Properties());
    }

}
