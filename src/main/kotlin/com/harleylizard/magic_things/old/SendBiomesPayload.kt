package com.harleylizard.magic_things.old.common.payload

import com.harleylizard.magic_things.old.common.MagicThings.Companion.resourceLocation
import com.harleylizard.magic_things.old.common.payload.Construct.Companion.construct
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload

@JvmRecord
data class SendBiomesPayload(val construct: Construct, val x: Int, val y: Int, val z: Int) : CustomPacketPayload {

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload?>? {
        return type
    }

    companion object {
        val codec = object : StreamCodec<FriendlyByteBuf, SendBiomesPayload> {
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

        val sendBiomes = "send_biomes".resourceLocation

        val type = CustomPacketPayload.Type<SendBiomesPayload>(sendBiomes)

    }

}