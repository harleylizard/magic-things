package com.harleylizard.magic_things.common

import com.harleylizard.magic_things.common.MagicThings.Companion.resourceLocation
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item

class MagicThingsItems {

    fun register() {

    }

    fun register(name: String, item: Item) {
        Registry.register(BuiltInRegistries.ITEM, name.resourceLocation, item)
    }

}