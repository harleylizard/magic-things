package com.harleylizard.magic_things.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public final class FouledGrowthBlock extends ShapeBlock {
    public static final IntegerProperty XZ_SIDE = IntegerProperty.create("xz_side", 0, 256);
    public static final IntegerProperty Y_SIDE = IntegerProperty.create("y_side", 0, 3);

    public FouledGrowthBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(XZ_SIDE, 0).setValue(Y_SIDE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        builder.add(XZ_SIDE);
        builder.add(Y_SIDE);
    }

    public static int fromBlockState(BlockState blockState) {
        var i = 0;

        int j = blockState.getValue(Y_SIDE);
        i |= j & 1;
        i |= ((j >> 1) & 1) << 1;

        int k = blockState.getValue(XZ_SIDE);
        i |= oneOrZero(k, 0) << 2;
        i |= oneOrZero(k, 1) << 3;
        i |= oneOrZero(k, 2) << 4;
        i |= oneOrZero(k, 3) << 5;

        return i;
    }

    public static BlockState toBlockState(int i) {
        return null;
    }

    private static int oneOrZero(int i, int j) {
        return ((i >> 2 * j) & 3) > 0 ? 1 : 0;
    }

}
