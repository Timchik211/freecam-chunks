package dev.example.freecamchunks;

import net.minecraft.world.phys.Vec3;

public final class CamState {
    /** Пишет клиент каждый тик. */
    public static volatile Vec3 camPos = null;
    /** Снимок позиции, который сервер фиксирует один раз за вызов move(). */
    public static Vec3 snapshot = null;
    private CamState() {}
}
