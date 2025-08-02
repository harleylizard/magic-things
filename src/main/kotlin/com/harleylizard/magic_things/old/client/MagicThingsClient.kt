package com.harleylizard.magic_things.old.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.color.block.BlockColor
import net.minecraft.client.color.item.ItemColor
import net.minecraft.client.renderer.BiomeColors
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.level.GrassColor

class MagicThingsClient : ClientModInitializer {
    override fun onInitializeClient() {
        ColorProviderRegistry.BLOCK.register(BlockColor { blockState, blockAndTintGetter, blockPos, i -> BiomeColors.getAverageGrassColor(blockAndTintGetter, blockPos) },
            MagicThingsBlocks.fouledGrowth,
            MagicThingsBlocks.sporePodStalk,
            MagicThingsBlocks.wiltingGrass)

        val item = ColorProviderRegistry.ITEM
        item.register(ItemColor { itemStack, i -> GrassColor.getDefaultColor() }, MagicThingsItems.wiltingGrass)
        item.register(ItemColor { itemStack, i -> 0x7D4FAD }, MagicThingsItems.fouledGrowth)

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
            MagicThingsBlocks.fouledSapling,
            MagicThingsBlocks.fouledGrowth,
            MagicThingsBlocks.sporePodStalk,
            MagicThingsBlocks.wiltingGrass)

    }

}