package com.harleylizard.magic_things.common.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
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

    override fun updateShape(blockState: BlockState, direction: Direction, blockState2: BlockState, levelAccessor: LevelAccessor, blockPos: BlockPos, blockPos2: BlockPos): BlockState? {
        val i = fromBlockState(blockState)

        for (direction in Direction.Plane.HORIZONTAL) {
        }

        return if (i == 0) Blocks.AIR.defaultBlockState() else reverse(i)
    }

    override fun getStateForPlacement(blockPlaceContext: BlockPlaceContext): BlockState {
        return defaultBlockState().setValue(sides, (3 shl (2 * 0)) or (1 shl (2 * 1)))
    }

    fun fromBlockState(blockState: BlockState): Int {
        var i = 0
        i = i or ((if (blockState.getValue(BlockStateProperties.UP)) 1 else 0) shl 0)
        i = i or ((if (blockState.getValue(BlockStateProperties.DOWN)) 1 else 0) shl 1)

        val j = blockState.getValue(sides)

        for (direction in Direction.Plane.HORIZONTAL) {
            val ordinal = direction.ordinal

            val value = (j shr (2 * (ordinal - 2))) and 3

            i = i or ((if (value > 0) 1 else 0) shl ordinal)
        }

        return i
    }

    fun reverse(i: Int): BlockState {
        var result = defaultBlockState()

        return result
    }

    companion object {
        val sides: IntegerProperty = IntegerProperty.create("sides", 0, 255)

    }
}