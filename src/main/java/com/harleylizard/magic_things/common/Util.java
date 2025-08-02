package com.harleylizard.magic_things.common;

import com.harleylizard.magic_things.common.block.Facing;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Iterator;

public final class Util {

    public static void setBiome(ServerLevel level, ResourceKey<Biome> biome, BlockPos blockPos) {
        var x = blockPos.getX();
        var y = blockPos.getY();
        var z = blockPos.getZ();

        var chunk = level.getChunk(x >> 4, z >> 4, ChunkStatus.FULL, false);
        if (chunk != null) {
            var section = chunk.getSection(chunk.getSectionIndex(y));

            var biomes = section.getBiomes().recreate();

            var holder = level.registryAccess().registry(Registries.BIOME).orElseThrow().getHolder(biome).orElseThrow();
            biomes.set(0, 0, 0, holder);

            ((BiomeSetter) section).magicThings$set(biomes);

            for (var player : PlayerLookup.tracking(level, blockPos)) {
                ServerPlayNetworking.send(player, SetBiomesPayload.from(biomes, x, y, z));
            }
        }
    }

    public static VoxelShape rotateShape(VoxelShape shape, Quaternionf rotation) {
        var matrix4f = new Matrix4f();
        matrix4f.identity();
        matrix4f.translate(0.5f, 0.5f, 0.5f);
        matrix4f.rotate(rotation);
        matrix4f.translate(-0.5f, -0.5f, -0.5f);

        var min = new Vector3f();
        var max = new Vector3f();

        var result = Shapes.empty();
        for (var aabb : shape.toAabbs()) {
            matrix4f.transformAab(
                    (float) aabb.minX,
                    (float) aabb.minY,
                    (float) aabb.minZ,

                    (float) aabb.maxX,
                    (float) aabb.maxY,
                    (float) aabb.maxZ,
                    min,
                    max
            );

            aabb = new AABB(min.x, min.y, min.z, max.x, max.y, max.z);
            result = Shapes.or(result, Shapes.create(aabb));

        }

        return result;
    }

    public static VoxelShape rotateShape(VoxelShape shape, Facing facing) {
        return rotateShape(shape, facing.getDegrees());
    }

    public static Iterator<Position> between(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
        var minX = Math.min(fromX, toX);
        var minY = Math.min(fromY, toY);
        var minZ = Math.min(fromZ, toZ);

        var maxX = Math.max(toX, fromX);
        var maxY = Math.max(toY, fromY);
        var maxZ = Math.max(toZ, fromZ);

        return new Iterator<>() {
            private final Position next = new Position();

            {
                next.x = minX;
                next.y = minY;
                next.z = minZ;
            }

            @Override
            public boolean hasNext() {
                return next.x <= maxX && next.y <= maxY && next.z <= maxZ;
            }

            @Override
            public Position next() {
                if (!hasNext()) {
                    throw new AssertionError();
                }

                if (++next.z > maxZ) {
                    next.z = minZ;
                    if (++next.y > maxY) {
                        next.y = minY;
                        next.x++;
                    }
                }
                return next;
            }
        };
    }

    public static final class Position {
        private int x;
        private int y;
        private int z;

        private Position() {}

        public BlockPos offset(BlockPos blockPos) {
            return blockPos.offset(x, y, z);
        }

    }

}
