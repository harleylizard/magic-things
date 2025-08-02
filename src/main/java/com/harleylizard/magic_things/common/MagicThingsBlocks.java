package com.harleylizard.magic_things.common;

import com.harleylizard.magic_things.common.block.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import java.util.Optional;

public final class MagicThingsBlocks {
    public static final Block FOULED_LOG = new RotatedPillarBlock(Properties.of().sound(SoundType.SLIME_BLOCK));
    public static final Block FOULED_LOG_ROOT = new FouledLogRootBlock(Properties.ofFullCopy(FOULED_LOG).noOcclusion());
    public static final Block FOULED_WOOD = new RotatedPillarBlock(Properties.ofFullCopy(FOULED_LOG));
    public static final Block FOULED_SAPLING = new FouledSaplingBlock(new TreeGrower("fouled", 0.1f, Optional.empty(), Optional.of(MagicThingsConfiguredFeatures.FOULED_TREE), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()), Properties.ofFullCopy(Blocks.OAK_SAPLING).sound(SoundType.SLIME_BLOCK));
    public static final Block FOULED_GROWTH = new FouledGrowthBlock(Properties.of().sound(SoundType.SLIME_BLOCK).noCollission().noOcclusion().randomTicks());
    public static final Block SPORE_POD = new SporePodBlock(Properties.of().sound(SoundType.SLIME_BLOCK).noCollission().noOcclusion().randomTicks());
    public static final Block SPORE_POD_STEM = new SporePodStalkBlock(Properties.ofFullCopy(SPORE_POD));
    public static final Block WILTING_GRASS = new TallGrassBlock(Properties.ofFullCopy(Blocks.SHORT_GRASS));

    public static void register() {
        register("fouled_log", FOULED_LOG);
        register("fouled_log_root", FOULED_LOG_ROOT);
        register("fouled_wood", FOULED_WOOD);
        register("fouled_sapling", FOULED_SAPLING);
        register("fouled_growth", FOULED_GROWTH);
        register("spore_pod", SPORE_POD);
        register("spore_pod_stem", SPORE_POD_STEM);
        register("wilting_grass", WILTING_GRASS);

    }

    public static void register(String name, Block block) {
        Registry.register(BuiltInRegistries.BLOCK, MagicThings.resourceLocation(name), block);
    }

}
