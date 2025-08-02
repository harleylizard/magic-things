package com.harleylizard.magic_things.common;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.TerraBlenderApi;

public final class MagicThings implements ModInitializer, TerraBlenderApi {
    public static final String MOD_ID = "magic-things";

    @Override
    public void onInitialize() {
        MagicThingsBlocks.register();
        MagicThingsItems.register();
    }

    @Override
    public void onTerraBlenderInitialized() {
    }

    public static ResourceLocation resourceLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}
