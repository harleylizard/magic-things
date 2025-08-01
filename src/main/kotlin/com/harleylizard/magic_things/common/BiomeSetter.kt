package com.harleylizard.magic_things.common

import net.minecraft.core.Holder
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.chunk.PalettedContainer

interface BiomeSetter {

    fun `magicThings$set`(biomes: PalettedContainer<Holder<Biome>>)

}