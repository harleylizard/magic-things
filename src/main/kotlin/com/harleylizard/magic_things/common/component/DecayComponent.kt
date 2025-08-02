package com.harleylizard.magic_things.common.component

import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.chunk.ChunkAccess
import org.ladysnake.cca.api.v3.component.ComponentV3
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent

class DecayComponent(val chunk: ChunkAccess) : ComponentV3, ServerTickingComponent {

    override fun readFromNbt(p0: CompoundTag, p1: HolderLookup.Provider) {
    }

    override fun writeToNbt(p0: CompoundTag, p1: HolderLookup.Provider) {
    }

    override fun serverTick() {
    }

}