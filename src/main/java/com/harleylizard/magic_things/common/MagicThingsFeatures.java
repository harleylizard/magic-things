package com.harleylizard.magic_things.common;

import com.harleylizard.magic_things.common.terrablender.FouledTreeFeature;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public final class MagicThingsFeatures {
    public static final Feature<NoneFeatureConfiguration> FOULED_TREE = new FouledTreeFeature();

    public static void register() {
        register("fouled_tree", FOULED_TREE);
    }

    public static void register(String name, Feature<? extends FeatureConfiguration> feature) {
        Registry.register(BuiltInRegistries.FEATURE, MagicThings.resourceLocation(name), feature);
    }

}
