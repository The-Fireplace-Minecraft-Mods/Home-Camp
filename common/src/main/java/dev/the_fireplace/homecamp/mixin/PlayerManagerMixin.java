package dev.the_fireplace.homecamp.mixin;

import dev.the_fireplace.homecamp.HomeCampConstants;
import dev.the_fireplace.homecamp.domain.config.ConfigValues;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;

@Mixin(PlayerList.class)
public final class PlayerManagerMixin
{
    @Inject(at = @At("RETURN"), method = "respawn", locals = LocalCapture.CAPTURE_FAILSOFT)
    private void extinguishCampfireAfterSpawning(
        ServerPlayer player,
        boolean alive,
        CallbackInfoReturnable<ServerPlayer> cir,
        @Nullable BlockPos spawnPointPos,
        float spawnAngle,
        boolean spawnIsSet,
        ServerLevel serverWorld
    ) {
        if (spawnPointPos == null) {
            return;
        }
        BlockState state = serverWorld.getBlockState(spawnPointPos);
        if (spawnIsSet && HomeCampConstants.getInjector().getInstance(ConfigValues.class).isExtinguishOnSpawn() && CampfireBlock.isLitCampfire(state)) {
            extinguishCampfire(serverWorld, spawnPointPos, state);
        }
    }

    private static void extinguishCampfire(ServerLevel world, BlockPos pos, BlockState state) {
        world.levelEvent(null, 1009, pos, 0);
        CampfireBlock.dowse(null, world, pos, state);
        world.setBlockAndUpdate(pos, state.setValue(CampfireBlock.LIT, false));
    }
}
