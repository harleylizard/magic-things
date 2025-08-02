package com.harleylizard.magic_things.common.terrablender;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public final class MagicThingsSurfaceRules {

    public static SurfaceRules.RuleSource fromBlock(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    public static SurfaceRules.RuleSource create() {
        return SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), fromBlock(Blocks.GRASS_BLOCK)), fromBlock(Blocks.DIRT)));
    }

}
