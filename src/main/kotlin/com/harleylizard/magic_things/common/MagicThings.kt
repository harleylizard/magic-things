package com.harleylizard.magic_things.common

import com.harleylizard.magic_things.common.terrablender.MagicThingsRegion
import com.harleylizard.magic_things.common.terrablender.MagicThingsSurfaceRules
import net.fabricmc.api.ModInitializer
import net.minecraft.resources.ResourceLocation
import terrablender.api.Regions
import terrablender.api.SurfaceRuleManager
import terrablender.api.TerraBlenderApi

class MagicThings : ModInitializer, TerraBlenderApi {

    override fun onInitialize() {
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