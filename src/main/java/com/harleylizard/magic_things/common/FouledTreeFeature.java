package com.harleylizard.magic_things.common;

import com.harleylizard.magic_things.common.block.Facing;
import com.harleylizard.magic_things.common.block.FouledLogRootBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public final class FouledTreeFeature extends Feature<NoneFeatureConfiguration> {

    public FouledTreeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context) {
        var random = context.random();
        var height = 4 + random.nextInt(4);

        var level = context.level();
        var pos = context.origin();

        var log = MagicThingsBlocks.FOULED_LOG.defaultBlockState();

        var canPlace = true;
        for (var i = 0; i < height; i++) {
            if (!level.getBlockState(pos.above(i)).is(MagicThingsBlockTags.FOULED_LOG_CAN_REPLACE)) {
                canPlace = false;
                break;
            }
        }

        if (canPlace) {
            for (var i = 0; i < height; i++) {
                level.setBlock(pos.above(i), log, Block.UPDATE_ALL);
            }

            var top = pos.above(height);
            var distance = 0;
            for (var facing : Facing.values()) {
                if (random.nextInt(1) == 0) {
                    var below = facing.relative(top);
                    var i = 0;
                    while (level.getBlockState(below).is(MagicThingsBlockTags.FOULED_GROWTH_CAN_REPLACE)) {
                        below = below.below();
                        if (i++ > 7) {
                            break;
                        }
                    }

                    var floor = below.above();

                    var blockState = level.getBlockState(floor);
                    if (blockState.getFluidState().isEmpty()) {
                        var root = FouledLogRootBlock.set(MagicThingsBlocks.FOULED_LOG_ROOT.defaultBlockState(), facing.getOpposite());

                        if (root.canSurvive(level, floor) && level.setBlock(floor, root, Block.UPDATE_ALL)) {
                            distance = Math.max(distance, top.getY() - floor.getY());
                            placeDirt(level, below);
                        }
                    }
                }
            }

            for (var i = 0; i < distance; i++) {
                level.setBlock(top.below(i + 1), log, Block.UPDATE_ALL);
            }
            return true;
        }

        return false;
    }

    private void placeDirt(WorldGenLevel level, BlockPos blockPos) {
        if (level.getBlockState(blockPos).is(BlockTags.REPLACEABLE)) {
            level.setBlock(blockPos, Blocks.DIRT.defaultBlockState(), Block.UPDATE_ALL);
        }

    }

}
