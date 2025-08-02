package com.harleylizard.magic_things.client;

import com.harleylizard.magic_things.common.MagicThingsBlocks;
import com.harleylizard.magic_things.common.MagicThingsItems;
import com.harleylizard.magic_things.common.SetBiomesPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.GrassColor;

import static java.util.Objects.requireNonNull;

public final class MagicThingsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(SetBiomesPayload.TYPE, (payload, context) -> {

        });

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
                MagicThingsBlocks.FOULED_SAPLING,
                MagicThingsBlocks.FOULED_GROWTH,
                MagicThingsBlocks.WILTING_GRASS
                );

        ColorProviderRegistry.BLOCK.register((blockState, blockAndTintGetter, blockPos, i) -> BiomeColors.getAverageGrassColor(requireNonNull(blockAndTintGetter), requireNonNull(blockPos)),
                MagicThingsBlocks.FOULED_GROWTH,
                MagicThingsBlocks.WILTING_GRASS);

        var itemColors = ColorProviderRegistry.ITEM;
        itemColors.register((itemStack, i) -> GrassColor.getDefaultColor(), MagicThingsItems.WILTING_GRASS);
        itemColors.register((itemStack, i) -> 0x7D4FAD, MagicThingsItems.FOULED_GROWTH);

    }

}
