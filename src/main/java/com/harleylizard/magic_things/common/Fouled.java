package com.harleylizard.magic_things.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

public final class Fouled {

    public static Boolean isNearbyBiome(Level level, BlockPos blockPos) {
        var iterator = Util.between(-3, -3, -3, 3, 3, 3);
        while (iterator.hasNext()) {
            if (level.getBiome(iterator.next().offset(blockPos)).is(MagicThingsBiomeTags.FOULED)) {
                return true;
            }

        }

        return false;
    }

    public static BlockState convertLog(Level level, BlockPos blockPos) {
        var blockState = level.getBlockState(blockPos);
        if (blockState.is(MagicThingsBlockTags.CONVERTS_INTO_FOULED_LOG)) {
            var log = MagicThingsBlocks.FOULED_LOG.defaultBlockState();

            if (blockState.getBlock() instanceof RotatedPillarBlock) {
                return log.setValue(RotatedPillarBlock.AXIS, blockState.getValue(RotatedPillarBlock.AXIS));
            }

            return log;
        }

        return blockState;

    }

}
