package com.harleylizard.magic_things.common;

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
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentInitializer;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public final class MagicThings implements ModInitializer, TerraBlenderApi, ChunkComponentInitializer {
    public static final String MOD_ID = "magic-things";

    public static final ComponentKey<DecayComponent> DECAY = ComponentRegistryV3.INSTANCE.getOrCreate(resourceLocation("decay"), DecayComponent.class);

    @Override
    public void onInitialize() {
        MagicThingsBlocks.register();
        MagicThingsItems.register();
        MagicThingsFeatures.register();

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, resourceLocation("creative_tab"), FabricItemGroup.builder().icon(Items.DIRT::getDefaultInstance).displayItems((itemDisplayParameters, output) -> {
            output.accept(MagicThingsItems.FOULED_LOG);
            output.accept(MagicThingsItems.FOULED_WOOD);
            output.accept(MagicThingsItems.FOULED_LOG_ROOT);
            output.accept(MagicThingsItems.FOULED_SAPLING);
            output.accept(MagicThingsItems.FOULED_GROWTH);
            output.accept(MagicThingsItems.WILTING_GRASS);

        }).title(Component.translatable("magic-things")).build());

        PayloadTypeRegistry.playS2C().register(SetBiomesPayload.TYPE, SetBiomesPayload.CODEC);
    }

    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new MagicThingsRegion(resourceLocation("overworld")));

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, MagicThingsSurfaceRules.create());
    }

    @Override
    public void registerChunkComponentFactories(@NotNull ChunkComponentFactoryRegistry registry) {
        registry.register(DECAY, DecayComponent::new);
    }

    public static ResourceLocation resourceLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}
