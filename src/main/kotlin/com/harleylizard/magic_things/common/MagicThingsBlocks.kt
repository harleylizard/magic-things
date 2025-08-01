package com.harleylizard.magic_things.common

import com.harleylizard.magic_things.common.MagicThings.Companion.resourceLocation
import com.harleylizard.magic_things.common.block.FouledLogRootsBlock
import com.harleylizard.magic_things.common.block.FouledSaplingBlock
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.TallGrassBlock
import net.minecraft.world.level.block.grower.TreeGrower
import net.minecraft.world.level.block.state.BlockBehaviour.Properties

object MagicThingsBlocks {
    val fouledLog = RotatedPillarBlock(Properties.of())
    val fouledLogRoots = FouledLogRootsBlock(Properties.of().noOcclusion())
    val fouledSapling = FouledSaplingBlock(TreeGrower.OAK, Properties.ofFullCopy(Blocks.OAK_SAPLING))
    val wiltingGrass = TallGrassBlock(Properties.ofFullCopy(Blocks.SHORT_GRASS))

    fun register() {
        register("fouled_log", fouledLog)
        register("fouled_log_roots", fouledLogRoots)
        register("fouled_sapling", fouledSapling)
        register("wilting_grass", wiltingGrass)

    }

    fun register(name: String, block: Block) {
        Registry.register(BuiltInRegistries.BLOCK, name.resourceLocation, block)
    }

}