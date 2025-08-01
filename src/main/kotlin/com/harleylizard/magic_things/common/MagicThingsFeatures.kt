package com.harleylizard.magic_things.common

import com.harleylizard.magic_things.common.MagicThings.Companion.resourceLocation
import com.harleylizard.magic_things.common.terrablender.FouledTreeFeature
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.levelgen.feature.Feature

object MagicThingsFeatures {
    val fouledTree = FouledTreeFeature()

    fun register() {
        register("fouled_tree", fouledTree)
    }

    fun register(name: String, feature: Feature<*>) {
        Registry.register(BuiltInRegistries.FEATURE, name.resourceLocation, feature)
    }

}