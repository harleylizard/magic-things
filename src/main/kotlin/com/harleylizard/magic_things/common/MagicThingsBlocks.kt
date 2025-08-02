package com.harleylizard.magic_things.common

import com.harleylizard.magic_things.common.MagicThings.Companion.resourceLocation
import com.harleylizard.magic_things.common.block.*
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.grower.TreeGrower
import net.minecraft.world.level.block.state.BlockBehaviour.Properties

object MagicThingsBlocks {
    val fouledLog = RotatedPillarBlock(Properties.of().sound(SoundType.SLIME_BLOCK))
    val fouledWood = RotatedPillarBlock(Properties.ofFullCopy(fouledLog))
    val fouledLogRoots = FouledLogRootsBlock(Properties.of().sound(SoundType.SLIME_BLOCK).noOcclusion())
    val fouledSapling = FouledSaplingBlock(Properties.ofFullCopy(Blocks.OAK_SAPLING).sound(SoundType.SLIME_BLOCK))
    val fouledGrowth = FouledGrowthBlock(Properties.of().sound(SoundType.SLIME_BLOCK).noCollission().noOcclusion().randomTicks())
    val sporePod = SporePodBlock(Properties.of().sound(SoundType.SLIME_BLOCK).noCollission().noOcclusion().randomTicks())
    val sporePodStalk = SporePodStalkBlock(Properties.ofFullCopy(sporePod))
    val wiltingGrass = TallGrassBlock(Properties.ofFullCopy(Blocks.SHORT_GRASS))

    fun register() {
        register("fouled_log", fouledLog)
        register("fouled_wood", fouledWood)
        register("fouled_log_roots", fouledLogRoots)
        register("fouled_sapling", fouledSapling)
        register("fouled_growth", fouledGrowth)
        register("spore_pod", sporePod)
        register("spore_pod_stalk", sporePodStalk)
        register("wilting_grass", wiltingGrass)

    }

    fun register(name: String, block: Block) {
        Registry.register(BuiltInRegistries.BLOCK, name.resourceLocation, block)
    }

}