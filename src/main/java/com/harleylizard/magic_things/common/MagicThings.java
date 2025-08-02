package com.harleylizard.magic_things.common;

import com.harleylizard.magic_things.common.payload.SendBiomesPayload;
import com.harleylizard.magic_things.common.terrablender.MagicThingsRegion;
import com.harleylizard.magic_things.common.terrablender.MagicThingsSurfaceRules;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentInitializer;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public final class MagicThings implements ModInitializer, TerraBlenderApi, ChunkComponentInitializer {
    public static final String MOD_ID = "magic-things";

    @Override
    public void onInitialize() {
        MagicThingsBlocks.register();
        MagicThingsItems.register();
        MagicThingsFeatures.register();

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, resourceLocation("creative_tab"), FabricItemGroup.builder().icon(Items.DIRT::getDefaultInstance).displayItems((itemDisplayParameters, output) -> {

        }).title(Component.translatable("magic-things")).build());

        PayloadTypeRegistry.playS2C().register(SendBiomesPayload.Companion.getType(), SendBiomesPayload.Companion.getCodec());
    }

    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new MagicThingsRegion(resourceLocation("overworld")));

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, MagicThingsSurfaceRules.create());
    }

    @Override
    public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {

    }

    public static ResourceLocation resourceLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}
