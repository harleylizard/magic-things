package com.harleylizard.magic_things.common

import com.harleylizard.magic_things.common.MagicThings.Companion.resourceLocation
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

object MagicThingsItems {
    val Block.blockItem get() = BlockItem(this, Item.Properties())

    val fouledLog = MagicThingsBlocks.fouledLog.blockItem
    val fouledLogRoots = MagicThingsBlocks.fouledLogRoots.blockItem
    val fouledSapling = MagicThingsBlocks.fouledSapling.blockItem
    val wiltingGrass = MagicThingsBlocks.wiltingGrass.blockItem

    fun register() {
        register("fouled_log", fouledLog)
        register("fouled_log_roots", fouledLogRoots)
        register("fouled_sapling", fouledSapling)
        register("wilting_grass", wiltingGrass)

    }

    fun register(name: String, item: Item) {
        Registry.register(BuiltInRegistries.ITEM, name.resourceLocation, item)
    }

}