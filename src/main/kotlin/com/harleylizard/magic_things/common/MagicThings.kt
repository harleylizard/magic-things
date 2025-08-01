package com.harleylizard.magic_things.common

import com.harleylizard.magic_things.common.payload.SendBiomesPayload
import com.harleylizard.magic_things.common.terrablender.MagicThingsRegion
import com.harleylizard.magic_things.common.terrablender.MagicThingsSurfaceRules
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items
import terrablender.api.Regions
import terrablender.api.SurfaceRuleManager
import terrablender.api.TerraBlenderApi

class MagicThings : ModInitializer, TerraBlenderApi {

    override fun onInitialize() {
        MagicThingsBlocks.register()
        MagicThingsItems.register()
        MagicThingsFeatures.register()

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, "creative_tab".resourceLocation, FabricItemGroup.builder().icon(Items.DIRT::getDefaultInstance).displayItems { itemDisplayParameters, output ->
            output.accept(MagicThingsBlocks.FOULED_LOG)
            output.accept(MagicThingsBlocks.FOULED_LOG_ROOTS)
            output.accept(MagicThingsBlocks.FOULED_SAPLING)

        }.title(Component.translatable("magic-things")).build())

        PayloadTypeRegistry.playS2C().register(SendBiomesPayload.TYPE, SendBiomesPayload.CODEC)

    }

    override fun onTerraBlenderInitialized() {
        Regions.register(MagicThingsRegion("overworld".resourceLocation))

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, MagicThingsSurfaceRules.surfaceRules())
    }

    companion object {
        const val MOD_ID = "magic-things"

        val String.resourceLocation: ResourceLocation get() = ResourceLocation.fromNamespaceAndPath(MOD_ID, this);

    }
}