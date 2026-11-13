package dev.example.freecamchunks.mixin;

import dev.example.freecamchunks.CamState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkMap.class)
public class ChunkLoadingManagerMixin {

    private static final String OF =
        "Lnet/minecraft/core/SectionPos;of(Lnet/minecraft/world/level/entity/EntityAccess;)Lnet/minecraft/core/SectionPos;";

    /** Фиксируем позицию камеры один раз за вызов move(). */
    @Inject(method = "move", at = @At("HEAD"))
    private void freecamchunks$snapshot(ServerPlayer player, CallbackInfo ci) {
        CamState.snapshot = CamState.camPos;
    }

    @Redirect(method = "move", at = @At(value = "INVOKE", target = OF))
    private SectionPos freecamchunks$moveSection(EntityAccess entity) {
        return freecamchunks$pick(entity);
    }

    /** Здесь сервер запоминает "последнюю секцию" игрока: она должна совпадать с move(). */
    @Redirect(method = "updatePlayerPos", at = @At(value = "INVOKE", target = OF))
    private SectionPos freecamchunks$updateSection(EntityAccess entity) {
        return freecamchunks$pick(entity);
    }

    private static SectionPos freecamchunks$pick(EntityAccess entity) {
        Vec3 cam = CamState.snapshot;
        if (cam != null && entity instanceof ServerPlayer) {
            return SectionPos.of(BlockPos.containing(cam));
        }
        return SectionPos.of(entity);
    }
}
