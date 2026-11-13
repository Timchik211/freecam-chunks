package dev.example.freecamchunks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3d;

public class FreecamChunksClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(FreecamChunksClient::tick);
    }

    private static void tick(MinecraftClient mc) {
        // Работает только в одиночной игре (есть встроенный сервер)
        if (mc.player == null || mc.getServer() == null) {
            CamState.camPos = null;
            return;
        }
        Camera cam = mc.gameRenderer.getCamera();
        if (cam == null || !cam.isReady()) {
            CamState.camPos = null;
            return;
        }
        Vec3d p = cam.getPos();
        // Если камера далеко от игрока (> 8 блоков) — считаем, что включён FreeCam
        CamState.camPos = p.squaredDistanceTo(mc.player.getPos()) > 64.0 ? p : null;
    }
}
