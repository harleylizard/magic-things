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
import net.minecraft.tags.BlockTags
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
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f

object Util {
    val horizontal: Array<Direction> = arrayOf(Direction.SOUTH, Direction.EAST, Direction.NORTH, Direction.WEST)

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

    fun solid(level: LevelReader, blockPos: BlockPos, direction: Direction) = level.getBlockState(blockPos).let {
        it.isFaceSturdy(level, blockPos, direction) && !it.propagatesSkylightDown(level, blockPos)
    }

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

    fun place(level: Level, blockPos: BlockPos, blockState: BlockState) {
        fun set(level: Level, blockPos: BlockPos, new: BlockState) {
            if (new.canSurvive(level, blockPos)) {
                level.setBlock(blockPos, new, Block.UPDATE_ALL)
            }
        }

        val replacing = level.getBlockState(blockPos)
        if (replacing.let { it.`is`(BlockTags.REPLACEABLE) || it.`is`(MagicThingsBlocks.fouledGrowth) && blockState != it }) {
            if (blockState.getValue(BlockStateProperties.DOWN)) {
                set(level, blockPos, FouledGrowthBlock.down(replacing))
            }

            if (blockState.getValue(BlockStateProperties.UP)) {
                set(level, blockPos, FouledGrowthBlock.down(replacing))
            }

        }

    }

    fun nearbyFouledBiome(level: Level, blockPos: BlockPos): Boolean {
        val distance = 4
        for (x in -distance until distance) {
            for (y in -distance until distance) {
                for (z in -distance until distance) {
                    if (level.getBiome(blockPos.offset(x, y, z)).`is`(MagicThingsBiomeTags.fouled)) {
                        return true
                    }

                }
            }
        }
        return false
    }

}