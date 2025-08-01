package com.harleylizard.magic_things.common.block

import com.harleylizard.magic_things.common.MagicThingsBlockTags
import com.harleylizard.magic_things.common.MagicThingsConfiguredFeatures
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.SaplingBlock
import net.minecraft.world.level.block.grower.TreeGrower
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class FouledSaplingBlock(treeGrower: TreeGrower, properties: Properties) : SaplingBlock(TreeGrower("fouled", 0.1f,
    Optional.empty(),
    Optional.empty(),
    Optional.of(MagicThingsConfiguredFeatures.fouledTree),
    Optional.empty(),
    Optional.empty(),
    Optional.empty()
), properties) {

    override fun mayPlaceOn(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos): Boolean {
        return blockState.`is`(MagicThingsBlockTags.fouledSaplingGrowsOn)
    }

}