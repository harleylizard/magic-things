package com.harleylizard.magic_things.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public final class MagicThingsConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> FOULED_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MagicThings.resourceLocation("fouled_tree"));

}
