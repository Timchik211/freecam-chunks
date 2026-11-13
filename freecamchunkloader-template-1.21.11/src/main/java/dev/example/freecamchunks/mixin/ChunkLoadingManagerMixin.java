package dev.example.freecamchunks.mixin;

import dev.example.freecamchunks.CamState;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerChunkLoadingManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerChunkLoadingManager.class)
public class ChunkLoadingManagerMixin {

    /** Сервер думает, что "центр загрузки чанков" игрока — это камера. */
    @Redirect(
        method = "updatePosition",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/util/math/ChunkSectionPos;from(Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/math/ChunkSectionPos;")
    )
    private ChunkSectionPos freecamchunks$useCameraPos(Entity entity) {
        Vec3d cam = CamState.camPos;
        if (cam != null && entity instanceof ServerPlayerEntity) {
            return ChunkSectionPos.from(BlockPos.ofFloored(cam));
        }
        return ChunkSectionPos.from(entity);
    }
}
