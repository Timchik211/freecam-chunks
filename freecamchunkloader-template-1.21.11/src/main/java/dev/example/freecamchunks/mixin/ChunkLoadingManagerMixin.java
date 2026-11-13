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
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChunkMap.class)
public class ChunkLoadingManagerMixin {

    @Redirect(
        method = "move",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/core/SectionPos;of(Lnet/minecraft/world/level/entity/EntityAccess;)Lnet/minecraft/core/SectionPos;")
    )
    private SectionPos freecamchunks$useCameraPos(EntityAccess entity) {
        Vec3 cam = CamState.camPos;
        if (cam != null && entity instanceof ServerPlayer) {
            return SectionPos.of(BlockPos.containing(cam));
        }
        return SectionPos.of(entity);
    }
}
