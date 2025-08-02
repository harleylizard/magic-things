package com.harleylizard.magic_things.common

import com.harleylizard.magic_things.common.MagicThings.Companion.resourceLocation
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block

object MagicThingsBlockTags {
    val decaysInFouledBiome = "decays_in_fouled_biome".tag
    val fouledSaplingGrowsOn = "fouled_sapling_grows_on".tag
    val fouledGrowthCanReplace = "fouled_growth_can_replace".tag

    val String.tag: TagKey<Block> get() = TagKey.create(Registries.BLOCK, resourceLocation)

}