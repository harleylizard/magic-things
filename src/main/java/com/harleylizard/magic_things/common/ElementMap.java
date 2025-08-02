package com.harleylizard.magic_things.common;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;

import java.util.Collection;

public final class ElementMap {
    private final Object2IntMap<Element> map;

    private ElementMap(Object2IntMap<Element> map) {
        this.map = map;
    }

    public Collection<Object2IntMap.Entry<Element>> entrySet() {
        return map.object2IntEntrySet();
    }

    public static final class Builder {
        private final Object2IntMap<Element> map = new Object2IntArrayMap<>();

        public ElementMap build() {
            return new ElementMap(Object2IntMaps.unmodifiable(map));
        }

    }

}
