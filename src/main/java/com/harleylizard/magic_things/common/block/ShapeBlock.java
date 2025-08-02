package com.harleylizard.magic_things.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public sealed class ShapeBlock extends Block permits FouledGrowthBlock, FouledLogRootBlock {
    private final Map<BlockState, VoxelShape> shapes = new HashMap<>();

    public ShapeBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
        return shapes.computeIfAbsent(blockState, this::shapeFrom);
    }

    public VoxelShape shapeFrom(BlockState blockState) {
        return Shapes.block();
    }

}
