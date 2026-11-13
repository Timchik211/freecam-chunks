package dev.example.freecamchunks;

import net.minecraft.world.phys.Vec3;

public final class CamState {
    public static volatile Vec3 camPos = null;
    private CamState() {}
}
