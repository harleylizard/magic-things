package com.harleylizard.magic_things.common.feature;

import com.harleylizard.magic_things.common.MagicThingsBlockTags;
import com.harleylizard.magic_things.common.MagicThingsBlocks;
import com.harleylizard.magic_things.common.block.Facing;
import com.harleylizard.magic_things.common.block.FouledLogRootBlock;
import net.minecraft.world.level.block.Block;
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
        var height = 4 + context.random().nextInt(4);

        var level = context.level();
        var pos = context.origin();
        for (var i = 0; i < height; i++) {
            if (!level.getBlockState(pos.above(i)).is(MagicThingsBlockTags.FOULED_LOG_CAN_REPLACE)) {
                return false;
            }
        }

        var top = pos.above(height);
        var distance = 0;
        for (var facing : Facing.values()) {
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
                var root = FouledLogRootBlock.set(MagicThingsBlocks.FOULED_LOG_ROOT.defaultBlockState(), facing);

                if (root.canSurvive(level, floor) && level.setBlock(floor, root, Block.UPDATE_ALL)) {
                    distance = Math.max(distance, top.getY() - floor.getY());
                }

            }


        }

        return true;
    }

}
