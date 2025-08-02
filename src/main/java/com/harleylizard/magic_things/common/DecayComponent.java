package com.harleylizard.magic_things.common;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentV3;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public final class DecayComponent implements ComponentV3, ServerTickingComponent {
    private final ChunkAccess chunk;

    public DecayComponent(ChunkAccess chunk) {
        this.chunk = chunk;
    }

    @Override
    public void serverTick() {

    }

    @Override
    public void readFromNbt(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registryLookup) {
    }

    @Override
    public void writeToNbt(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registryLookup) {
    }

}
