package com.harleylizard.magic_things.common.block;

import com.harleylizard.magic_things.common.MagicThingsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    @Override
    protected boolean canSurvive(@NotNull BlockState blockState, @NotNull LevelReader level, @NotNull BlockPos blockPos) {
        return rebuild(blockState, level, blockPos) > 0;
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState blockState, @NotNull Direction direction, @NotNull BlockState blockState2, @NotNull LevelAccessor level, @NotNull BlockPos blockPos, @NotNull BlockPos blockPos2) {
        int i;
        return (i = rebuild(blockState, level, blockPos)) == 0 ? Blocks.AIR.defaultBlockState() : toBlockState(i);
    }

    public int rebuild(BlockState blockState, LevelReader level, BlockPos blockPos) {
        var i = fromBlockState(blockState);

        for (var neighbour : Direction.values()) {
            if (!hasStudyFace(level, blockPos.relative(neighbour.getOpposite()), neighbour)) {
                i &= ~(3 << (2 * neighbour.ordinal()));
            }

        }

        return i;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();

        var face = context.getClickedFace();
        switch (face) {
            case UP, DOWN -> {
                return defaultBlockState().setValue(Y_SIDE, (hasStudyFace(level, pos.relative(face.getOpposite()), face) ? 1 : 0) << face.ordinal());
            }

        }
        return null;
    }

    @Override
    public VoxelShape shapeFrom(BlockState blockState) {
        int i = blockState.getValue(Y_SIDE);
        var shape = Shapes.empty();
        if ((i & 1) == 1) shape = Shapes.or(shape, Shapes.box(0.0d, 15.0d / 16.0d, 0.0d, 1.0d, 1.0d, 1.0d));
        if (((i >> 1) & 1) == 1) shape = Shapes.or(shape, Shapes.box(0.0d, 0.0d, 0.0d, 1.0d, 1.0d / 16.0d, 1.0d));

        return shape;
    }

    public boolean hasStudyFace(BlockGetter getter, BlockPos blockPos, Direction direction) {
        return getter.getBlockState(blockPos).isFaceSturdy(getter, blockPos, direction);
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
        j |= ((i >> 0) & 1);
        j |= ((i >> 2) & 1) << 1;

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
