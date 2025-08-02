package com.harleylizard.magic_things.common.block;

import com.mojang.math.Axis;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

public enum Facing implements StringRepresentable {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static final EnumProperty<Facing> FACING = EnumProperty.create("facing", Facing.class);

    private static final String[] NAMES = {"north", "east", "south", "west"};

    private static final Facing[] OPPOSITE = {SOUTH, WEST, NORTH, EAST};

    @Override
    public @NotNull String getSerializedName() {
        return NAMES[ordinal()];
    }

    public Facing getOpposite() {
        return OPPOSITE[ordinal()];
    }

    public Quaternionf getDegrees() {
        return Axis.YP.rotationDegrees(ordinal() * 90f);
    }

    public BlockPos relative(BlockPos blockPos) {
        var i = ordinal();
        var x = (i & 1) != 0 ? -(i - 2) : 0;
        var z = (i & 1) == 0 ? i - 1 : 0;
        return new BlockPos(blockPos.getX() + x, blockPos.getY(), blockPos.getZ() + z);
    }

}
