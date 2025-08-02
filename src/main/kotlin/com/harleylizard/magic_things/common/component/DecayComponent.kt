package com.harleylizard.magic_things.common.component

import com.harleylizard.magic_things.common.MagicThingsBiomeTags
import com.harleylizard.magic_things.common.MagicThingsBlockTags
import com.harleylizard.magic_things.common.Util.to
import com.harleylizard.magic_things.common.Util.until
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.chunk.ChunkAccess
import net.minecraft.world.level.chunk.LevelChunk
import org.ladysnake.cca.api.v3.component.ComponentV3
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent

class DecayComponent(val chunk: ChunkAccess) : ComponentV3, ServerTickingComponent {

    override fun readFromNbt(p0: CompoundTag, p1: HolderLookup.Provider) {
    }

    override fun writeToNbt(p0: CompoundTag, p1: HolderLookup.Provider) {
    }

    override fun serverTick() {
        if (chunk is LevelChunk) {
            val level = chunk.level
            val random = level.random
            if (random.nextInt(23) == 0) {
                val sections = chunk.sections
                val chunkPos = chunk.chunkPos
                loop@ for (i in chunk.minSection until chunk.maxSection) {
                    val section = sections[chunk.getSectionIndexFromSectionY(i)]

                    if (section.states.registry.any { blockState -> blockState.`is`(MagicThingsBlockTags.decaysInFouledBiome) }) {
                        if (random.nextInt(sections.size) == 0) {
                            ((0 to 0 to 0) until (16 to 16 to 16)).forEach {
                                val blockPos = BlockPos(it.x + (chunkPos.x shl 4), it.y + (i shl 4), it.z + (chunkPos.z shl 4))

                                if (!level.getBiome(blockPos).`is`(MagicThingsBiomeTags.fouled)) {
                                    continue@loop
                                }

                                if (random.nextInt(15) == 0) {
                                    val blockState = chunk.getBlockState(blockPos)
                                    if (blockState.`is`(MagicThingsBlockTags.decaysInFouledBiome)) {
                                        level.destroyBlock(blockPos, true)
                                    }

                                }

                            }
                        }
                    }
                }

            }

        }

    }

}