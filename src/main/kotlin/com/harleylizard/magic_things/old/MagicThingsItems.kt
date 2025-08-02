package com.harleylizard.magic_things.old.common

import com.harleylizard.magic_things.common.MagicThingsBlocks
import com.harleylizard.magic_things.common.item.SporePodItem
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

object MagicThingsItems {
    val Block.blockItem get() = BlockItem(this, Item.Properties())

    val fouledLog = MagicThingsBlocks.fouledLog.blockItem
    val fouledWood = MagicThingsBlocks.fouledWood.blockItem
    val fouledLogRoots = MagicThingsBlocks.fouledLogRoots.blockItem
    val fouledSapling = MagicThingsBlocks.fouledSapling.blockItem
    val fouledGrowth = MagicThingsBlocks.fouledGrowth.blockItem
    val sporePod = SporePodItem(Item.Properties())
    val wiltingGrass = MagicThingsBlocks.wiltingGrass.blockItem

    fun register() {
        register("fouled_log", fouledLog)
        register("fouled_wood", fouledWood)
        register("fouled_log_roots", fouledLogRoots)
        register("fouled_sapling", fouledSapling)
        register("fouled_growth", fouledGrowth)
        register("spore_pod", sporePod)
        register("wilting_grass", wiltingGrass)

    }

    fun register(name: String, item: Item) {
        Registry.register(BuiltInRegistries.ITEM, name.resourceLocation, item)
    }

}