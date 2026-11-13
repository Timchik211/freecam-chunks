package dev.example.freecamchunks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public class FreecamChunksClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(FreecamChunksClient::tick);
    }

    private static void tick(Minecraft mc) {
        if (mc.player == null || mc.getSingleplayerServer() == null) {
            CamState.camPos = null;
            return;
        }
        Camera cam = mc.gameRenderer.getMainCamera();
        if (cam == null || !cam.isInitialized()) {
            CamState.camPos = null;
            return;
        }
        Vec3 p = cam.position();
        CamState.camPos = p.distanceToSqr(mc.player.position()) > 64.0 ? p : null;
    }
}
