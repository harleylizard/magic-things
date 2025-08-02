package com.harleylizard.magic_things.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class FouledLogRootBlock extends Block {

    public FouledLogRootBlock(Properties properties) {
        super(properties);
    }

    public static BlockState set(BlockState blockState, Facing facing) {
        return blockState.setValue(Facing.FACING, facing);
    }

    public static Facing get(BlockState blockState) {
        return blockState.getValue(Facing.FACING);
    }

}
