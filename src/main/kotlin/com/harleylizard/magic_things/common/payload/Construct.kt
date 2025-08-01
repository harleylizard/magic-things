package com.harleylizard.magic_things.common.payload

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap
import it.unimi.dsi.fastutil.ints.Int2IntMap
import it.unimi.dsi.fastutil.ints.Int2IntMaps
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.chunk.PalettedContainer
import net.minecraft.world.level.chunk.PalettedContainerRO

class Construct(private val map: Int2IntMap) {

    fun get(buf: FriendlyByteBuf) {
        buf.writeInt(map.size)
        for (entry in map.int2IntEntrySet()) {
            buf.writeInt(entry.intKey)
            buf.writeInt(entry.intValue)
        }

    }

    fun <T> add(source: PalettedContainerRO<T>): PalettedContainer<T> {
        val copy = source.recreate()

        return copy
    }

    companion object {
        val FriendlyByteBuf.construct: Construct get() {
            val size = readInt()
            val map: Int2IntMap = Int2IntArrayMap(size)

            for (i in 0..size) {
                map.put(
                    readInt(),
                    readInt())
            }

            return Construct(Int2IntMaps.unmodifiable(map))
        }

        val <T> PalettedContainer<T>.construct: Construct get() {
            val palette = data.palette
            val size = palette.size
            val map: Int2IntMap = Int2IntArrayMap(size)

            val registry = registry
            for (i in 0..palette.size) {
                map.put(i, registry.getId(palette.valueFor(i)))
            }

            return Construct(Int2IntMaps.unmodifiable(map))

        }

    }
}