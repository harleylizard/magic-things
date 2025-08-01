package com.harleylizard.magic_things.common.block

import com.harleylizard.magic_things.common.MagicThingsBlocks
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

class FouledLogRootsBlock(properties: Properties) : Block(properties) {

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
                return isTopSolid(levelReader, blockPos.below())
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

    fun isTopSolid(level: LevelReader, blockPos: BlockPos) = level.getBlockState(blockPos).let { it.isFaceSturdy(level, blockPos, Direction.UP) && !it.propagatesSkylightDown(level, blockPos) }

    fun nextToLog(level: LevelReader, blockPos: BlockPos, direction: Direction) = level.getBlockState(blockPos.relative(direction)).`is`(MagicThingsBlocks.fouledLog)

}