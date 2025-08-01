package com.harleylizard.magic_things.common.terrablender

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.SurfaceRules

object MagicThingsSurfaceRules {
    val Block.surfaceRule: SurfaceRules.RuleSource get() = SurfaceRules.state(defaultBlockState())

    fun surfaceRules(): SurfaceRules.RuleSource {
        return SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), Blocks.GRASS_BLOCK.surfaceRule), Blocks.DIRT.surfaceRule))
    }

}