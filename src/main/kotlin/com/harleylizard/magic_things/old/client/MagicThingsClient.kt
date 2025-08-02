package com.harleylizard.magic_things.old.client

import com.harleylizard.magic_things.old.common.MagicThingsItems
import com.harleylizard.magic_things.old.common.payload.SendBiomesPayload
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.color.block.BlockColor
import net.minecraft.client.color.item.ItemColor
import net.minecraft.client.renderer.BiomeColors
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.level.GrassColor
import net.minecraft.world.level.chunk.status.ChunkStatus

class MagicThingsClient : ClientModInitializer {
    override fun onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(SendBiomesPayload.Companion.type) { payload, context ->
            context.client()?.let {
                it.execute {
                    val x = payload.x shr 4
                    val z = payload.z shr 4

                    it.level?.getChunk(x, z, ChunkStatus.FULL, false)?.let { chunk ->
                        chunk.isUnsaved = true

                        val section = chunk.getSection(chunk.getSectionIndex(payload.y))

                        (section as BiomeSetter).`magicThings$set`(payload.construct.add(section.biomes))

                    }
                }
            }
        }

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