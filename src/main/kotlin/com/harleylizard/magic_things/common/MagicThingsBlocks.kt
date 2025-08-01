package com.harleylizard.magic_things.common

import com.harleylizard.magic_things.common.MagicThings.Companion.resourceLocation
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.Block

object MagicThingsBlocks {

    fun register() {

    }

    fun register(name: String, block: Block) {
        Registry.register(BuiltInRegistries.BLOCK, name.resourceLocation, block)
    }

}