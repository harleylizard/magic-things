package com.harleylizard.magic_things.common.block;

import com.harleylizard.magic_things.common.Util;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public final class FouledLogRootBlock extends ShapeBlock {

    public FouledLogRootBlock(Properties properties) {
        super(properties);
        registerDefaultState(set(getStateDefinition().any(), Facing.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        builder.add(Facing.FACING);
    }

    @Override
    public @NotNull BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return set(defaultBlockState(), Facing.fromDirection(context.getHorizontalDirection()));
    }

    @Override
    public VoxelShape shapeFrom(BlockState blockState) {
        return Util.rotateShape(Shapes.or(Shapes.box(0.3125, 0.0, 0.3125, 0.6875, 0.3125, 0.6875), Shapes.box(0.3125, 0.3125, 0.0, 0.6875, 0.6875, 0.6875)), get(blockState));
    }

    public static BlockState set(BlockState blockState, Facing facing) {
        return blockState.setValue(Facing.FACING, facing);
    }

    public static Facing get(BlockState blockState) {
        return blockState.getValue(Facing.FACING);
    }

}
