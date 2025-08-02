package com.harleylizard.magic_things.common.terrablender

import com.harleylizard.magic_things.common.MagicThingsBlocks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration

class FouledTreeFeature() : Feature<NoneFeatureConfiguration>(NoneFeatureConfiguration.CODEC) {

    override fun place(context: FeaturePlaceContext<NoneFeatureConfiguration>): Boolean {
        val level = context.level()
        val random = context.random()
        val blockPos = context.origin()

        val height = 4 + random.nextInt(4)

        val top = blockPos.above(height)

        var floor = top.y
        for (direction in Direction.Plane.HORIZONTAL) {
            if (random.nextInt(8) == 0) {
                continue
            }
            var relative = top.relative(direction)

            var i = 0
            while (level.getBlockState(relative.below().also { relative = it }).let { it.`is`(BlockTags.REPLACEABLE) || it.`is`(MagicThingsBlocks.fouledGrowth) }) {
                if (i++ > 8) {
                    break
                }
            }

            val lowest = relative.above()

            if (level.getBlockState(lowest).let { it.fluidState.isEmpty && replaceable(it) }) {
                val root = MagicThingsBlocks.fouledLogRoots.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, direction.opposite)

                level.setBlock(lowest, root, Block.UPDATE_ALL)

                floor = Math.min(floor, lowest.y)
            }
        }

        for (i in floor..top.y) {
            val relative =  BlockPos(blockPos.x, i, blockPos.z)

            level.getBlockState(relative).takeIf { replaceable(it) || it.`is`(BlockTags.DIRT) }?.let {
                level.setBlock(relative, MagicThingsBlocks.fouledLog.defaultBlockState(), Block.UPDATE_ALL)
            }

        }

        return true
    }

    fun replaceable(blockState: BlockState) = blockState.`is`(BlockTags.REPLACEABLE_BY_TREES) || blockState.`is`(BlockTags.REPLACEABLE) || blockState.`is`(BlockTags.LUSH_GROUND_REPLACEABLE)

}