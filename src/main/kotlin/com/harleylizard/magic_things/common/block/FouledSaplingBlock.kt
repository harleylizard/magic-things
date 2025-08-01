package com.harleylizard.magic_things.common.block

import com.harleylizard.magic_things.common.MagicThingsBlockTags
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.SaplingBlock
import net.minecraft.world.level.block.grower.TreeGrower
import net.minecraft.world.level.block.state.BlockState

class FouledSaplingBlock(treeGrower: TreeGrower, properties: Properties) : SaplingBlock(treeGrower, properties) {

    override fun mayPlaceOn(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos): Boolean {
        return blockState.`is`(MagicThingsBlockTags.FOULED_SAPLING_GROWS_ON)
    }

}