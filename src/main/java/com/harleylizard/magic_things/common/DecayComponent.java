package com.harleylizard.magic_things.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
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
        if (chunk instanceof LevelChunk levelChunk) {
            var pos = levelChunk.getPos();
            var chunkX = pos.x << 4;
            var chunkZ = pos.z << 4;

            var level = levelChunk.getLevel();

            var random = level.random;
            if (random.nextInt(15) == 0) {
                var sections = levelChunk.getSections();

                for (var i = levelChunk.getMinSection(); i < levelChunk.getMaxSection(); i++) {
                    if (random.nextInt(sections.length) == 0) {
                        var section = sections[levelChunk.getSectionIndexFromSectionY(i)];

                        if (section.getStates().maybeHas(blockState -> blockState.is(MagicThingsBlockTags.DECAYS_IN_FOULED_BIOME))) {
                            var iterator = Util.between(0, 0, 0, 16, 16, 16);
                            while (iterator.hasNext()) {
                                var blockPos = iterator.next().offset(new BlockPos(chunkX, i << 4, chunkZ));

                                if (random.nextInt(15) == 0 && level.getBiome(blockPos).is(MagicThingsBiomeTags.FOULED)) {
                                    if (levelChunk.getBlockState(blockPos).is(MagicThingsBlockTags.DECAYS_IN_FOULED_BIOME)) {
                                        level.destroyBlock(blockPos, true);
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void readFromNbt(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registryLookup) {
    }

    @Override
    public void writeToNbt(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registryLookup) {
    }

}
