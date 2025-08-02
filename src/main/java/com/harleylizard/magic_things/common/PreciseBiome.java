package com.harleylizard.magic_things.common;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class PreciseBiome {
    private final NonNullList<Section> sections = NonNullList.withSize(16, EmptySection.EMPTY);

    public void set(BlockPos pos, Holder<Biome> holder) {
        var i = sectionY(pos);
        var section = sections.get(i);
        if (section == EmptySection.EMPTY) {
            section = new FilledSection();
            section.set(pos, holder);

            sections.set(i, section);
            return;
        }

        section.set(pos, holder);
    }

    public void remove(BlockPos pos) {
        var i = sectionY(pos);
        var section = sections.get(i);
        if (section.isEmpty()) {
            return;
        }

        section.remove(pos);

        if (section.isEmpty()) {
            sections.set(i, EmptySection.EMPTY);
        }
    }

    @Nullable
    public Holder<Biome> get(BlockPos pos) {
        return sections.get(sectionY(pos)).get(pos);
    }

    private int sectionY(BlockPos pos) {
        return pos.getY() >> 4;
    }

    private sealed interface Section {

        void set(BlockPos pos, Holder<Biome> holder);

        void remove(BlockPos pos);

        @Nullable
        Holder<Biome> get(BlockPos pos);

        boolean isEmpty();

    }

    private static final class EmptySection implements Section {
        private static final Section EMPTY = new EmptySection();

        private EmptySection() {}

        @Override
        public void set(BlockPos pos, Holder<Biome> holder) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove(BlockPos pos) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Holder<Biome> get(BlockPos pos) {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

    }

    private static final class FilledSection implements Section {
        private final Sparsely<Holder<Biome>> sparsely = new Sparsely<>();

        private int[] grid = new int[16 * 16 * 16];

        @Override
        public void set(BlockPos pos, Holder<Biome> holder) {
            //var i = biomes.indexOf(requireNonNull(holder)); // redo
            //if (i == -1) {
            //    biomes.add(holder);
            //    i = biomes.size();
            //}

            //var j = indexOf(pos);

            //i++;
            //set(i).add(j);
            //grid[j] = i;
        }

        @Override
        public void remove(BlockPos pos) {
            var i = indexOf(pos);
            var j = grid[i];
            if (j > 0) {
                grid[i] = 0;

                sparsely.remove(j, i);
            }

        }

        @Nullable
        public Holder<Biome> get(BlockPos pos) {
            return isEmpty() ? null : sparsely.get(grid[indexOf(pos)]);
        }

        @Override
        public boolean isEmpty() {
            return sparsely.isEmpty();
        }

        private int indexOf(BlockPos pos) {
            var x = pos.getX() & 0xF;
            var y = pos.getY() & 0xF;
            var z = pos.getZ() & 0xF;

            return x + y * 16 + z * 16 * 16;
        }

    }

    public static final class Sparsely<T> {
        private final Int2ObjectMap<IntSet> indices = new Int2ObjectArrayMap<>();

        private final List<T> list = new ArrayList<>(); // todo:: use int map

        private void remove(int key, int j) {
            if (indices.containsKey(key)) {
                var set = indices.get(key);
                set.remove(j);

                if (set.isEmpty()) {
                    list.remove(key - 1);
                }

            }

        }

        @Nullable
        private T get(int key) {
            return key <= 0 || list.isEmpty() ? null : list.get(key - 1);
        }

        private int get(T t) {
            return list.indexOf(t);
        }

        private boolean isEmpty() {
            return list.isEmpty();
        }

    }

}
