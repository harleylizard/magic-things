package com.harleylizard.magic_things.common

import com.harleylizard.magic_things.common.MagicThings.Companion.resourceLocation
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.level.biome.Biome

object MagicThingsBiomeTags {
    val fouled: TagKey<Biome> = TagKey.create(Registries.BIOME, "fouled".resourceLocation)

}