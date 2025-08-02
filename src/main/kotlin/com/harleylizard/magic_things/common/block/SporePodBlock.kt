package com.harleylizard.magic_things.common.block

import com.harleylizard.magic_things.common.MagicThingsItems
import net.minecraft.core.BlockPos
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

class SporePodBlock(properties: Properties) : Block(properties) {

    override fun getCloneItemStack(levelReader: LevelReader, blockPos: BlockPos, blockState: BlockState): ItemStack? {
        return MagicThingsItems.sporePod.defaultInstance
    }
}