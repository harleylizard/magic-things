package com.harleylizard.magic_things.common

import com.harleylizard.magic_things.common.MagicThings.Companion.resourceLocation
import com.harleylizard.magic_things.common.block.FouledLogRootsBlock
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.BlockBehaviour.Properties

object MagicThingsBlocks {
    val FOULED_LOG = RotatedPillarBlock(Properties.of())
    val FOULED_LOG_ROOTS = FouledLogRootsBlock(Properties.of().noOcclusion())

    fun register() {
        register("fouled_log", FOULED_LOG)
        register("fouled_log_roots", FOULED_LOG_ROOTS)

    }

    fun register(name: String, block: Block) {
        Registry.register(BuiltInRegistries.BLOCK, name.resourceLocation, block)
    }

}