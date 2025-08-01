package com.harleylizard.magic_things.common

import com.harleylizard.magic_things.common.MagicThings.Companion.resourceLocation
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

object MagicThingsItems {
    val Block.blockItem get() = BlockItem(this, Item.Properties())

    val FOULED_LOG = MagicThingsBlocks.FOULED_LOG.blockItem
    val FOULED_LOG_ROOTS = MagicThingsBlocks.FOULED_LOG_ROOTS.blockItem
    val FOULED_SAPLING = MagicThingsBlocks.FOULED_SAPLING.blockItem

    fun register() {
        register("fouled_log", FOULED_LOG)
        register("fouled_log_roots", FOULED_LOG_ROOTS)
        register("fouled_sapling", FOULED_SAPLING)

    }

    fun register(name: String, item: Item) {
        Registry.register(BuiltInRegistries.ITEM, name.resourceLocation, item)
    }

}