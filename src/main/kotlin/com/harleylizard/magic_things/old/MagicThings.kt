package com.harleylizard.magic_things.old.common

import com.harleylizard.magic_things.common.MagicThingsBlocks
import com.harleylizard.magic_things.common.MagicThingsFeatures
import com.harleylizard.magic_things.old.common.component.DecayComponent
import net.fabricmc.api.ModInitializer
import net.minecraft.resources.ResourceLocation
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