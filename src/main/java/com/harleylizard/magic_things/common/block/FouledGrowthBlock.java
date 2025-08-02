package com.harleylizard.magic_things.common.block;

import com.harleylizard.magic_things.common.MagicThingsBlocks;
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
        i |= ((j >> 0) & 1) << 2 * 0;
        i |= ((j >> 1) & 1) << 2 * 1;

        int k = blockState.getValue(XZ_SIDE);
        i |= growthStage(k, 0) << 2 * 2;
        i |= growthStage(k, 1) << 2 * 3;
        i |= growthStage(k, 2) << 2 * 4;
        i |= growthStage(k, 3) << 2 * 5;

        return i;
    }

    public static BlockState toBlockState(int i) {
        var j = 0;
        j |= ((i >> 0) & 1) << 0;
        j |= ((i >> 1) & 1) << 1;

        var blockState = MagicThingsBlocks.FOULED_GROWTH.defaultBlockState().setValue(Y_SIDE, j);

        var k = 0;
        k |= growthStage(i, 2) << 2 * 0;
        k |= growthStage(i, 3) << 2 * 1;
        k |= growthStage(i, 4) << 2 * 2;
        k |= growthStage(i, 5) << 2 * 3;

        return blockState.setValue(XZ_SIDE, k);
    }

    private static int growthStage(int i, int j) {
        return (i >> 2 * j) & 3;
    }

}
