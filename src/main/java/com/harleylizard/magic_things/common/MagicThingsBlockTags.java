package com.harleylizard.magic_things.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class MagicThingsBlockTags {
    public static final TagKey<Block> DECAYS_IN_FOUL_BIOME = tagOf("decays_in_foul_biome");
    public static final TagKey<Block> FOULED_GROWTH_CAN_REPLACE = tagOf("fouled_growth_can_replace");
    public static final TagKey<Block> FOULED_LOG_CAN_REPLACE = tagOf("fouled_log_can_replace");
    public static final TagKey<Block> FOULED_SAPLING_GROWS_ON = tagOf("fouled_sapling_grows_on");

    public static TagKey<Block> tagOf(String name) {
        return TagKey.create(Registries.BLOCK, MagicThings.resourceLocation(name));
    }

}
