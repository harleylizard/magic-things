package com.harleylizard.magic_things.common.terrablender

import com.harleylizard.magic_things.common.MagicThingsBlocks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.WorldGenLevel
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration

class FouledTreeFeature() : Feature<NoneFeatureConfiguration>(NoneFeatureConfiguration.CODEC) {

    override fun place(context: FeaturePlaceContext<NoneFeatureConfiguration>): Boolean {
        val level = context.level()
        val blockPos = context.origin()

        val random = context.random()

        val log = MagicThingsBlocks.FOULED_LOG.defaultBlockState()
        val height = 4 + random.nextInt(3)
        for (i in 0..height) {
            level.setBlock(blockPos.above(i), log, Block.UPDATE_ALL)
        }

        var lowest = 0
        for (direction in Direction.Plane.HORIZONTAL) {
            if (random.nextInt(8) == 0) {
                continue
            }

            val offset = blockPos.relative(direction)

            var i = 0
            var below = offset
            while (!level.getBlockState(offset.below(i).also { below = it }).isFaceSturdy(level, below, Direction.UP) && i < 8) {
                i++
            }
            val rootBlockPos = below.above()

            if (canReplace(level, rootBlockPos)) {
                val root = MagicThingsBlocks.FOULED_LOG_ROOTS.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, direction.opposite)

                if (root.canSurvive(level, rootBlockPos) && level.setBlock(below.above(), root, Block.UPDATE_ALL)) {
                    lowest = lowest.coerceAtLeast(i)
                }
            }
        }

        for (i in lowest - 1 downTo 0) {
            level.setBlock(blockPos.below(i), log, Block.UPDATE_ALL)
        }

        return true
    }

    fun canReplace(level: WorldGenLevel, blockPos: BlockPos) = level.getBlockState(blockPos).let { it.fluidState.isEmpty && (it.`is`(BlockTags.REPLACEABLE_BY_TREES) || it.`is`(BlockTags.REPLACEABLE)) }

}