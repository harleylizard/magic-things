package com.harleylizard.magic_things.common.terrablender

import com.mojang.datafixers.util.Pair
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.Climate
import terrablender.api.ParameterUtils
import terrablender.api.Region
import terrablender.api.RegionType
import java.util.function.Consumer

class MagicThingsRegion(name: ResourceLocation) : Region(name, RegionType.OVERWORLD, 5) {

    override fun addBiomes(registry: Registry<Biome>, mapper: Consumer<Pair<Climate.ParameterPoint?, ResourceKey<Biome>>>) {
        addBiome(mapper, ParameterUtils.Temperature.WARM.parameter(), ParameterUtils.Humidity.NEUTRAL.parameter(), ParameterUtils.Continentalness.INLAND.parameter(), ParameterUtils.Erosion.EROSION_3.parameter(), ParameterUtils.Weirdness.LOW_SLICE_VARIANT_ASCENDING.parameter(), ParameterUtils.Depth.SURFACE.parameter(), 0.0f, MagicThingsBiomes.FOULED_FOREST)
        addBiome(mapper, ParameterUtils.Temperature.WARM.parameter(), ParameterUtils.Humidity.NEUTRAL.parameter(), ParameterUtils.Continentalness.INLAND.parameter(), ParameterUtils.Erosion.EROSION_3.parameter(), ParameterUtils.Weirdness.LOW_SLICE_VARIANT_ASCENDING.parameter(), ParameterUtils.Depth.SURFACE.parameter(), 0.0f, MagicThingsBiomes.FOULED_PLAINS)

    }

}