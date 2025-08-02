package com.harleylizard.magic_things.common

import com.harleylizard.magic_things.common.component.DecayComponent
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
import org.ladysnake.cca.api.v3.chunk.ChunkComponentFactoryRegistry
import org.ladysnake.cca.api.v3.chunk.ChunkComponentInitializer
import org.ladysnake.cca.api.v3.component.ComponentKey
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3
import terrablender.api.Regions
import terrablender.api.SurfaceRuleManager
import terrablender.api.TerraBlenderApi

class MagicThings : ModInitializer, TerraBlenderApi, ChunkComponentInitializer {

    override fun onInitialize() {
        MagicThingsBlocks.register()
        MagicThingsItems.register()
        MagicThingsFeatures.register()

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, "creative_tab".resourceLocation, FabricItemGroup.builder().icon(Items.DIRT::getDefaultInstance).displayItems { itemDisplayParameters, output ->
            output.accept(MagicThingsItems.fouledLog)
            output.accept(MagicThingsItems.fouledWood)
            output.accept(MagicThingsItems.fouledLogRoots)
            output.accept(MagicThingsItems.fouledSapling)
            output.accept(MagicThingsItems.fouledGrowth)
            output.accept(MagicThingsItems.sporePod)
            output.accept(MagicThingsItems.wiltingGrass)

        }.title(Component.translatable("magic-things")).build())

        PayloadTypeRegistry.playS2C().register(SendBiomesPayload.type, SendBiomesPayload.codec)

    }

    override fun onTerraBlenderInitialized() {
        Regions.register(MagicThingsRegion("overworld".resourceLocation))

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, MagicThingsSurfaceRules.surfaceRules())
    }

    override fun registerChunkComponentFactories(registry: ChunkComponentFactoryRegistry) {
        registry.register(decay, ::DecayComponent)
    }

    companion object {
        const val MOD_ID = "magic-things"

        val String.resourceLocation: ResourceLocation get() = ResourceLocation.fromNamespaceAndPath(MOD_ID, this);

        val decay: ComponentKey<DecayComponent> = ComponentRegistryV3.INSTANCE.getOrCreate("decay".resourceLocation, DecayComponent::class.java)
    }
}