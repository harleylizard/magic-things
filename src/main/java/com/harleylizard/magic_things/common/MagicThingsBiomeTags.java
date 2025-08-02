package com.harleylizard.magic_things.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class MagicThingsBiomeTags {
    public static final TagKey<Biome> FOULED = TagKey.create(Registries.BIOME, MagicThings.resourceLocation("fouled"));

}
