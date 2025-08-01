package com.harleylizard.magic_things.common

import com.harleylizard.magic_things.common.payload.Construct.Companion.construct
import com.harleylizard.magic_things.common.payload.SendBiomesPayload
import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.chunk.status.ChunkStatus

object Util {

    fun set(level: ServerLevel, biome: ResourceKey<Biome>, blockPos: BlockPos) {
        val x = blockPos.x
        val y = blockPos.y
        val z = blockPos.z

        level.getChunk(x.shr(4), z.shr(4), ChunkStatus.FULL, false)?.let { chunk ->
            val section = chunk.getSection(chunk.getSectionIndex(y))

            val biomes = section.biomes.recreate()

            (section as BiomeSetter).`magicThings$set`(biomes)

            for (player in PlayerLookup.tracking(level, blockPos)) {
                ServerPlayNetworking.send(player, SendBiomesPayload(biomes.construct, x, y, z))
            }

        }

    }
}