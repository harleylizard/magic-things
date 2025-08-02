package com.harleylizard.magic_things.common.block

import com.harleylizard.magic_things.common.MagicThingsBlocks
import com.harleylizard.magic_things.common.Util
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.phys.shapes.VoxelShape

class FouledLogRootsBlock(properties: Properties) : Block(properties) {
    val shapes = mutableMapOf<BlockState, VoxelShape>()

    init {
        registerDefaultState(stateDefinition.any().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING)
    }

    override fun updateShape(blockState: BlockState, direction: Direction, blockState2: BlockState, levelAccessor: LevelAccessor, blockPos: BlockPos, blockPos2: BlockPos): BlockState? {
        return blockState.takeIf { canSurvive(blockState, levelAccessor, blockPos) } ?: Blocks.AIR.defaultBlockState()
    }

    override fun canSurvive(blockState: BlockState, levelReader: LevelReader, blockPos: BlockPos): Boolean {
        for (direction in Direction.Plane.HORIZONTAL) {
            if (nextToLog(levelReader, blockPos, direction)) {
                return Util.solid(levelReader, blockPos.below(), Direction.UP)
            }
        }

        return false
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        return defaultBlockState().let {
            for (direction in Direction.Plane.HORIZONTAL) {
                if (nextToLog(context.level, context.clickedPos, direction)) {
                    return@let it.setValue(BlockStateProperties.HORIZONTAL_FACING, direction)
                }
            }
            it
        }
    }

    fun nextToLog(level: LevelReader, blockPos: BlockPos, direction: Direction) = level.getBlockState(blockPos.relative(direction)).`is`(MagicThingsBlocks.fouledLog)

}