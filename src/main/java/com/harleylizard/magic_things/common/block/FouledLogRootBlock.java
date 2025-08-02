package com.harleylizard.magic_things.common.block;

import com.harleylizard.magic_things.common.Util;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class FouledLogRootBlock extends ShapeBlock {

    public FouledLogRootBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape shapeFrom(BlockState blockState) {
        return Util.rotateShape(shapeOf(), get(blockState));
    }

    public VoxelShape shapeOf() {
        return Shapes.or(Shapes.box(0.3125, 0.0, 0.3125, 0.6875, 0.3125, 0.6875), Shapes.box(0.3125, 0.3125, 0.0, 0.6875, 0.6875, 0.6875));
    }

    public static BlockState set(BlockState blockState, Facing facing) {
        return blockState.setValue(Facing.FACING, facing);
    }

    public static Facing get(BlockState blockState) {
        return blockState.getValue(Facing.FACING);
    }

}
