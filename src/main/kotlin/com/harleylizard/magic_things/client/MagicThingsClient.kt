package com.harleylizard.magic_things.client

import com.harleylizard.magic_things.common.BiomeSetter
import com.harleylizard.magic_things.common.payload.SendBiomesPayload
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.world.level.chunk.status.ChunkStatus

class MagicThingsClient : ClientModInitializer {
    override fun onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(SendBiomesPayload.TYPE) { payload, context ->
            context.client()?.let {
                it.execute {
                    val x = payload.x.shr(4)
                    val z = payload.z.shr(4)

                    it.level?.getChunk(x, z, ChunkStatus.FULL, false)?.let { chunk ->
                        chunk.isUnsaved = true

                        val section = chunk.getSection(chunk.getSectionIndex(payload.y))

                        (section as BiomeSetter).`magicThings$set`(payload.construct.add(section.biomes))

                    }
                }
            }

        }

    }

}