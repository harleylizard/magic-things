package com.harleylizard.magic_things.common

import com.harleylizard.magic_things.common.block.FouledGrowthBlock
import com.harleylizard.magic_things.common.payload.Construct.Companion.construct
import com.harleylizard.magic_things.common.payload.SendBiomesPayload
import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.chunk.status.ChunkStatus
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import org.joml.*
import java.util.*

object Util {

    fun set(level: ServerLevel, biome: ResourceKey<Biome>, blockPos: BlockPos) {
        val x = blockPos.x
        val y = blockPos.y
        val z = blockPos.z

        level.getChunk(x shr 4, z shr 4, ChunkStatus.FULL, false)?.let { chunk ->
            val section = chunk.getSection(chunk.getSectionIndex(y))

            val biomes = section.biomes.recreate()

            (section as BiomeSetter).`magicThings$set`(biomes)

            for (player in PlayerLookup.tracking(level, blockPos)) {
                ServerPlayNetworking.send(player, SendBiomesPayload(biomes.construct, x, y, z))
            }

        }

    }

    fun solid(level: LevelReader, blockPos: BlockPos, direction: Direction) = level.getBlockState(blockPos).let { it.isFaceSturdy(level, blockPos, direction) && !it.propagatesSkylightDown(level, blockPos) }

    fun rotate(shape: VoxelShape, quaternionf: Quaternionf): VoxelShape {
        val matrix4f = Matrix4f()
        matrix4f.identity()
        matrix4f.translate(0.5f, 0.5f, 0.5f)
        matrix4f.rotate(quaternionf)
        matrix4f.translate(-0.5f, -0.5f, -0.5f)

        var rotated = Shapes.empty()
        for (box in shape.toAabbs().stream().map { aabb ->
            val min = Vector3f()
            val max = Vector3f()
            matrix4f.transformAab(
                aabb.minX.toFloat(), aabb.minY.toFloat(), aabb.minZ.toFloat(),
                aabb.maxX.toFloat(), aabb.maxY.toFloat(), aabb.maxZ.toFloat(), min, max)

            AABB(
                min.x.toDouble(), min.y.toDouble(), min.z.toDouble(),
                max.x.toDouble(), max.y.toDouble(), max.z.toDouble())

        }.toList()) {
            rotated = Shapes.or(rotated, Shapes.create(box))
        }

        return rotated
    }

    fun place(level: Level, blockPos: BlockPos, blockState: BlockState, random: RandomSource) {
        fun set(level: Level, blockPos: BlockPos, new: BlockState) {
            if (new.canSurvive(level, blockPos)) {
                level.setBlock(blockPos, new, Block.UPDATE_ALL)
            }
        }

        val replacing = level.getBlockState(blockPos)
        if (replacing.`is`(MagicThingsBlockTags.fouledGrowthCanReplace)) {
            if (blockState.getValue(BlockStateProperties.DOWN) && random.nextInt(5) == 0) {
                set(level, blockPos, FouledGrowthBlock.down(replacing))
            }

            if (blockState.getValue(BlockStateProperties.UP) && random.nextInt(5) == 0) {
                set(level, blockPos, FouledGrowthBlock.up(replacing))
            }

        }

    }

    fun nearbyFouledBiome(level: Level, blockPos: BlockPos) = (blockPos.offset(-4, -4, -4) until blockPos.offset(4, 4, 4)).any {
        level.getBiome(blockPos.offset(it)).`is`(MagicThingsBiomeTags.fouled)
    }

    operator fun BlockPos.component1() = x

    operator fun BlockPos.component2() = y

    operator fun BlockPos.component3() = z

    infix fun BlockPos.until(blockPos: BlockPos): Iterable<BlockPos> = BlockPos.betweenClosed(this, blockPos)

    infix fun Int.to(y: Int) = Vector2i(this, y)

    infix fun Vector2i.to(z: Int) = Vector3i(x, y, z)

    infix fun Vector3i.until(vector3i: Vector3i): Iterator<Vector3i> {
        val minX = x.coerceAtMost(vector3i.x)
        val minY = y.coerceAtMost(vector3i.y)
        val minZ = z.coerceAtMost(vector3i.z)
        val maxX = x.coerceAtLeast(vector3i.x)
        val maxY = y.coerceAtLeast(vector3i.y)
        val maxZ = z.coerceAtLeast(vector3i.z)

        val set: MutableSet<Vector3i> = HashSet((maxX - minX) * (maxY - minY) * (maxZ - minZ))
        for (x in minX until maxX) {
            for (y in minY until maxY) {
                for (z in minZ until maxZ) {
                    set += Vector3i(x, y, z)
                }
            }
        }
        return Collections.unmodifiableSet(set).iterator()
    }

    inline fun <T> Iterable<T>.any(test: (T) -> Boolean): Boolean {
        for (t in this) {
            if (test(t)) return true
        }
        return false
    }

}