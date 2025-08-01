package com.harleylizard.magic_things.common.terrablender

import com.harleylizard.magic_things.common.MagicThings.Companion.resourceLocation
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.biome.Biome

object MagicThingsBiomes {
    val fouledForest = "fouled_forest".resourceKey
    val fouledPlains = "fouled_plains".resourceKey

    val String.resourceKey: ResourceKey<Biome> get() = ResourceKey.create(Registries.BIOME, resourceLocation)

}