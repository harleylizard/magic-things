package com.harleylizard.magic_things.common;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

public final class Element {
    public static final ResourceKey<Registry<Element>> RESOURCE_KEY = ResourceKey.createRegistryKey(MagicThings.resourceLocation("element"));
    
    public static final Registry<Element> REGISTRY = FabricRegistryBuilder.createSimple(RESOURCE_KEY).attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public static final Element AIR = of();
    public static final Element EARTH = of();
    public static final Element FIRE = of();
    public static final Element WATER = of();

    private Element() {}
    
    public static void register() {
        register("air", AIR);
        register("earth", EARTH);
        register("fire", FIRE);
        register("water", WATER);

    }

    public boolean is(TagKey<Element> tag) {
        return REGISTRY.getHolderOrThrow(REGISTRY.getResourceKey(this).orElseThrow()).is(tag);
    }
    
    public static void register(String name, Element element) {
        Registry.register(REGISTRY, MagicThings.resourceLocation(name), element);
    }

    public static Element of() {
        return new Element();
    }
    
}
