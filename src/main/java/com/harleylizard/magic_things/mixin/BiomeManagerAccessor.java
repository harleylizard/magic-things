package com.harleylizard.magic_things.mixin;

import net.minecraft.world.level.biome.BiomeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BiomeManager.class)
public interface BiomeManagerAccessor {

    @Accessor("biomeZoomSeed")
    long magicThings$biomeSeedZoom();
    
    @Invoker("getFiddledDistance")
    static double magicThings$getFiddledDistance(long l, int i, int j, int k, double d, double e, double f) {
        throw new AssertionError();
    }

}
