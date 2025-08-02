package com.harleylizard.magic_things.common.block

import com.harleylizard.magic_things.common.MagicThingsBlocks
import com.harleylizard.magic_things.common.MagicThingsItems
import com.harleylizard.magic_things.common.Util
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties

class SporePodStalkBlock(properties: Properties) : Block(properties) {

    override fun getCloneItemStack(levelReader: LevelReader, blockPos: BlockPos, blockState: BlockState): ItemStack? {
        return MagicThingsItems.sporePod.defaultInstance
    }

    override fun randomTick(blockState: BlockState, level: ServerLevel, blockPos: BlockPos, random: RandomSource) {
        if (level.getBiome(blockPos).`is`(MagicThingsBiomeTags.fouled) && random.nextInt(15) == 0) {
            Util.spreadGrowthOutwards(level, blockPos, MagicThingsBlocks.fouledGrowth.defaultBlockState().setValue(BlockStateProperties.DOWN, true), random)
        }

    }

}