package com.harleylizard.magic_things.common.block

import com.harleylizard.magic_things.common.MagicThingsBlocks
import com.harleylizard.magic_things.common.Util
import com.mojang.math.Axis
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
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
            if (adjacentTo(levelReader, blockPos, direction)) {
                return Util.solid(levelReader, blockPos.below(), Direction.UP)
            }
        }

        return false
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val blockState = defaultBlockState()
        for (direction in Direction.Plane.HORIZONTAL) {
            if (adjacentTo(context.level, context.clickedPos, direction)) {
                return blockState.setValue(BlockStateProperties.HORIZONTAL_FACING, direction)
            }
        }

        return blockState
    }

    override fun getShape(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos, collisionContext: CollisionContext): VoxelShape? {
        return shapes.computeIfAbsent(blockState, this::shapeOf)
    }

    fun shapeOf(blockState: BlockState): VoxelShape {
        val shape = Shapes.or(Shapes.box(0.3125, 0.0, 0.3125, 0.6875, 0.3125, 0.6875), Shapes.box(0.3125, 0.3125, 0.0, 0.6875, 0.6875, 0.6875))

        return Util.rotate(shape, Axis.YP.rotationDegrees(y(blockState.getValue(BlockStateProperties.HORIZONTAL_FACING))))
    }

    // todo:: map properly
    fun y(direction: Direction): Float {
        return when (direction) {
            Direction.NORTH -> 0.0f
            Direction.EAST -> 270.0f
            Direction.SOUTH -> 180.0f
            Direction.WEST -> 90.0f
            else -> 0.0f
        }
    }

    fun adjacentTo(level: LevelReader, blockPos: BlockPos, direction: Direction) = level.getBlockState(blockPos.relative(direction)).`is`(MagicThingsBlocks.fouledLog)

}