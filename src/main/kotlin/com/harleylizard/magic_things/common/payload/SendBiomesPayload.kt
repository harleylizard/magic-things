package com.harleylizard.magic_things.common.payload

import com.harleylizard.magic_things.common.MagicThings.Companion.resourceLocation
import com.harleylizard.magic_things.common.payload.Construct.Companion.construct
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload

@JvmRecord
data class SendBiomesPayload(val construct: Construct, val x: Int, val y: Int, val z: Int) : CustomPacketPayload {

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload?>? {
        return TYPE
    }

    companion object {
        val CODEC = object : StreamCodec<FriendlyByteBuf, SendBiomesPayload> {
            override fun decode(buf: FriendlyByteBuf) = SendBiomesPayload(buf.construct,
                buf.readInt(),
                buf.readInt(),
                buf.readInt())

            override fun encode(buf: FriendlyByteBuf, payload: SendBiomesPayload) {
                payload.construct.get(buf)
                buf.writeInt(payload.x)
                buf.writeInt(payload.y)
                buf.writeInt(payload.z)
            }

        }

        val SEND_BIOMES = "send_biomes".resourceLocation

        val TYPE = CustomPacketPayload.Type<SendBiomesPayload>(SEND_BIOMES)

    }

}