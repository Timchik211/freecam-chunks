package dev.example.freecamchunks;

import net.minecraft.util.math.Vec3d;

/** Общее состояние: клиент пишет позицию камеры, интегрированный сервер читает. */
public final class CamState {
    public static volatile Vec3d camPos = null;
    private CamState() {}
}
