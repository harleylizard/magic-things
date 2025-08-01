package com.harleylizard.magic_things.common

import com.harleylizard.magic_things.common.MagicThings.Companion.resourceLocation
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature

object MagicThingsConfiguredFeatures {
    val fouledTree: ResourceKey<ConfiguredFeature<*, *>> = ResourceKey.create(Registries.CONFIGURED_FEATURE, "fouled_tree".resourceLocation)

}