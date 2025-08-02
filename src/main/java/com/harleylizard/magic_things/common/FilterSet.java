package com.harleylizard.magic_things.common;

import com.google.gson.JsonArray;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class FilterSet<T> {
    private final Set<Filter<T>> set;

    private FilterSet(Set<Filter<T>> set) {
        this.set = set;
    }

    public boolean compare(Holder<T> t) {
        for (var filter : set) {
            if (filter.test(t)) {
                if (filter.exclusive()) {
                    return false;
                }

                continue;
            }

            return false;
        }

        return true;
    }

    public static <T> FilterSet<T> parseFrom(JsonArray array, HolderLookup.RegistryLookup<T> lookup) {
        Set<Filter<T>> set = new HashSet<>(array.size());
        for (var entry : array) {
            var filter = Filter.find(entry.getAsString(), lookup);

            if (filter != null) {
                set.add(filter);
            }
        }

        return new FilterSet<>(Collections.unmodifiableSet(set));
    }

}
