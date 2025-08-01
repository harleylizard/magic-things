package com.harleylizard.magic_things.common

import com.harleylizard.magic_things.common.MagicThings.Companion.resourceLocation
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block

object MagicThingsBlockTags {
    val fouledSaplingGrowsOn: TagKey<Block> = TagKey.create(Registries.BLOCK, "fouled_sapling_grows_on".resourceLocation)

}