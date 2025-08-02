package com.harleylizard.magic_things.common;

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntMaps;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.PalettedContainer;
import org.jetbrains.annotations.NotNull;

public record SetBiomesPayload(Int2IntMap map, int x, int y, int z) implements CustomPacketPayload {
    public static final ResourceLocation SET_BIOMES = MagicThings.resourceLocation("set_biomes");

    public static final CustomPacketPayload.Type<SetBiomesPayload> TYPE = new Type<>(SET_BIOMES);

    public static final StreamCodec<FriendlyByteBuf, SetBiomesPayload> CODEC = new StreamCodec<>() {
        @Override
        public @NotNull SetBiomesPayload decode(@NotNull FriendlyByteBuf buf) {
            var size = buf.readInt();
            Int2IntMap map = new Int2IntArrayMap(size);
            for (var i = 0; i < size; i++) {
                map.put(
                        buf.readInt(),
                        buf.readInt()
                );
            }

            return new SetBiomesPayload(Int2IntMaps.unmodifiable(map),
                    buf.readInt(),
                    buf.readInt(),
                    buf.readInt());
        }

        @Override
        public void encode(@NotNull FriendlyByteBuf buf, @NotNull SetBiomesPayload payload) {
            var map = payload.map;
            buf.writeInt(map.size());
            for (var entry : map.int2IntEntrySet()) {
                buf.writeInt(entry.getIntKey());
                buf.writeInt(entry.getIntValue());
            }

            buf.writeInt(payload.x);
            buf.writeInt(payload.y);
            buf.writeInt(payload.z);

        }
    };

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static SetBiomesPayload from(PalettedContainer<Holder<Biome>> container, int x, int y, int z) {
        var registry = container.registry;

        var iterator = registry.iterator();
        Int2IntMap map = new Int2IntArrayMap();
        while (iterator.hasNext()) {
            var biome = iterator.next();

            map.put(registry.getId(biome), container.data.palette().idFor(biome));
        }

        return new SetBiomesPayload(Int2IntMaps.unmodifiable(map), x, y, z);
    }

}
