package com.harleylizard.magic_things.common.block;

import com.harleylizard.magic_things.common.MagicThingsBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public final class FouledSaplingBlock extends SaplingBlock {

    public FouledSaplingBlock(TreeGrower grower, Properties properties) {
        super(grower, properties);
    }

    @Override
    protected boolean mayPlaceOn(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        return blockState.is(MagicThingsBlockTags.FOULED_SAPLING_GROWS_ON);
    }

}
