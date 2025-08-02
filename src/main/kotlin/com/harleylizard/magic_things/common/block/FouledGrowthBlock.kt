package com.harleylizard.magic_things.common.block

import com.harleylizard.magic_things.common.Util
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.IntegerProperty

class FouledGrowthBlock(properties: Properties) : Block(properties) {

    init {
        registerDefaultState(stateDefinition.any()
            .setValue(BlockStateProperties.UP, false)
            .setValue(BlockStateProperties.DOWN, false).setValue(sides, 0))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        builder.add(BlockStateProperties.UP).add(BlockStateProperties.DOWN).add(sides)
    }

    override fun canSurvive(blockState: BlockState, level: LevelReader, blockPos: BlockPos): Boolean {
        return fromBlockState(level, blockPos, blockState) > 0
    }

    override fun updateShape(blockState: BlockState, direction: Direction, blockState2: BlockState, level: LevelAccessor, blockPos: BlockPos, blockPos2: BlockPos): BlockState? {
        return fromBlockState(level, blockPos, blockState).takeUnless { it == 0 }?.let { blockState } ?: Blocks.AIR.defaultBlockState()
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val level = context.level
        val blockPos = context.clickedPos

        val blockState = defaultBlockState()

        val face = context.clickedFace
        if (canGrowOn(level, blockPos, face)) {
            return when (face) {
                Direction.UP -> blockState.setValue(BlockStateProperties.DOWN, true)
                Direction.DOWN -> blockState.setValue(BlockStateProperties.UP, true)

                else -> blockState.setValue(sides, 2 shl offset(face.ordinal))
            }
        }

        return blockState
    }

    override fun isRandomlyTicking(blockState: BlockState): Boolean {
        return fromBlockState(blockState) > 0 && super.isRandomlyTicking(blockState)
    }

    override fun randomTick(blockState: BlockState, serverLevel: ServerLevel, blockPos: BlockPos, randomSource: RandomSource) {
    }

    fun fromBlockState(level: LevelReader, blockPos: BlockPos, blockState: BlockState): Int {
        var i = fromBlockState(blockState)

        for (direction in Direction.entries) {
            if (!canGrowOn(level, blockPos, direction)) {
                i = i and (1 shl direction.ordinal).inv()
            }
        }
        return i
    }

    fun fromBlockState(blockState: BlockState): Int {
        var i = 0
        i = i or (has(blockState, BlockStateProperties.UP) shl 0)
        i = i or (has(blockState, BlockStateProperties.DOWN) shl 1)

        val j = blockState.getValue(sides)

        return i
    }

    fun reverse(i: Int): BlockState {
        var result = defaultBlockState()

        return result
    }

    fun offset(i: Int) = 2 * (i - 2)

    fun has(blockState: BlockState, face: BooleanProperty) = has(blockState.getValue(face))

    fun has(boolean: Boolean) = if (boolean) 1 else 0

    fun canGrowOn(level: LevelReader, blockPos: BlockPos, direction: Direction) = Util.solid(level, blockPos.relative(direction.opposite), direction)

    companion object {
        val sides: IntegerProperty = IntegerProperty.create("sides", 0, 256)

    }
}