package com.harleylizard.magic_things.mixin;

import com.harleylizard.magic_things.common.BiomeSetter;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LevelChunkSection.class)
public final class LevelChunkSectionMixin implements BiomeSetter {

    @Shadow private PalettedContainerRO<Holder<Biome>> biomes;

    @Override
    public void magicThings$set(@NotNull PalettedContainer<@NotNull Holder<@NotNull Biome>> biomes) {
        this.biomes = biomes;
    }

}
