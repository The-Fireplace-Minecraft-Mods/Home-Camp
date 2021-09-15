package the_fireplace.homecamp.mixin;

import dev.the_fireplace.annotateddi.api.DIContainer;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import the_fireplace.homecamp.domain.config.ConfigValues;

import javax.annotation.Nullable;

@Mixin(PlayerManager.class)
public final class PlayerManagerMixin {
    @Inject(at = @At("RETURN"), method = "respawnPlayer", locals = LocalCapture.CAPTURE_FAILSOFT)
    private void extinguishCampfireAfterSpawning(
        ServerPlayerEntity player,
        boolean alive,
        CallbackInfoReturnable<ServerPlayerEntity> cir,
        @Nullable BlockPos spawnPointPos,
        float spawnAngle,
        boolean spawnIsSet,
        ServerWorld serverWorld
    ) {
        if (spawnPointPos == null) {
            return;
        }
        BlockState state = serverWorld.getBlockState(spawnPointPos);
        if (spawnIsSet && DIContainer.get().getInstance(ConfigValues.class).isExtinguishOnSpawn() && CampfireBlock.isLitCampfire(state)) {
            extinguishCampfire(serverWorld, spawnPointPos, state);
        }
    }

    private static void extinguishCampfire(ServerWorld world, BlockPos pos, BlockState state) {
        world.syncWorldEvent(null, 1009, pos, 0);
        CampfireBlock.extinguish(world, pos, state);
        world.setBlockState(pos, state.with(CampfireBlock.LIT, false));
    }
}
