package com.harleylizard.magic_things.common;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public sealed interface Filter<T> extends Predicate<Holder<T>> {

    boolean exclusive();

    @Deprecated
    private static boolean fromMod(String mod) {
        return FabricLoader.getInstance().isModLoaded(mod) || mod.equals("minecraft");
    }

    @Nullable
    static <T> Filter<T> find(String entry, HolderLookup.Provider provider, ResourceKey<Registry<T>> key) {
        if (entry.startsWith("!")) {
            return new ExcludeFilter<>(find(entry.substring(entry.indexOf("!") + 1), provider, key));
        }

        var registry = provider.lookup(key).orElseThrow();

        if (entry.startsWith("#")) {
            var location = ResourceLocation.parse(entry.substring(entry.indexOf("#") + 1));

            if (fromMod(location.getPath())) {
                var result = registry.listTagIds().filter(tag -> tag.location() == location).findAny();

                if (result.isPresent()) {
                    return new TagFilter<>(result.get());
                }
            }

            return null;
        }

        var location = ResourceLocation.parse(entry);

        if (fromMod(location.getPath())) {
            var result = registry.listElementIds().filter(resourceKey -> resourceKey.location() == location).findAny();

            if (result.isPresent()) {
                return new ObjectFilter<>(result.get());
            }
        }

        return null;
    }

    final class TagFilter<T> implements Filter<T> {
        private final TagKey<T> tag;

        private TagFilter(TagKey<T> tag) {
            this.tag = tag;
        }

        @Override
        public boolean test(Holder<T> holder) {
            return holder.is(tag);
        }

        @Override
        public boolean exclusive() {
            return false;
        }

    }

    final class ObjectFilter<T> implements Filter<T> {
        private final ResourceKey<T> key;

        private ObjectFilter(ResourceKey<T> key) {
            this.key = key;
        }

        @Override
        public boolean test(Holder<T> holder) {
            return holder.is(key);
        }

        @Override
        public boolean exclusive() {
            return false;
        }

    }

    final class ExcludeFilter<T> implements Filter<T> {
        private final Filter<T> filter;

        private ExcludeFilter(Filter<T> filter) {
            this.filter = filter;
        }

        @Override
        public boolean test(Holder<T> holder) {
            return !filter.test(holder);
        }

        @Override
        public boolean exclusive() {
            return true;
        }

    }

}
