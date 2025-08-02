package com.harleylizard.magic_things.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public final class Fouled {

    public static Boolean isNearbyBiome(Level level, BlockPos blockPos) {
        var iterator = Maths.between(-3, -3, -3, 3, 3, 3);
        while (iterator.hasNext()) {
            if (level.getBiome(iterator.next().offset(blockPos)).is(MagicThingsBiomeTags.FOULED)) {
                return true;
            }

        }

        return false;
    }

}
