package com.harleylizard.magic_things.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public final class MagicThingsBiomes {
    public static final ResourceKey<Biome> FOULED_FOREST = biomeOf("fouled_forest");
    public static final ResourceKey<Biome> FOULED_PLAINS = biomeOf("fouled_plains");

    public static ResourceKey<Biome> biomeOf(String name) {
        return ResourceKey.create(Registries.BIOME, MagicThings.resourceLocation(name));
    }

}
