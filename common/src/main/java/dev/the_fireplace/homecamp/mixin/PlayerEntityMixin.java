package dev.the_fireplace.homecamp.mixin;

import com.google.inject.Injector;
import dev.the_fireplace.homecamp.CampfireSpawnEligibility;
import dev.the_fireplace.homecamp.HomeCampConstants;
import dev.the_fireplace.lib.api.teleport.injectables.SafePosition;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Player.class)
public final class PlayerEntityMixin
{
    @Inject(at = @At("HEAD"), method = "findRespawnPositionAndUseSpawnBlock", cancellable = true)
    private static void findRespawnPosition(ServerLevel world, BlockPos pos, float f, boolean bl, boolean bl2, CallbackInfoReturnable<Optional<Vec3>> infoReturnable) {
        BlockState state = world.getBlockState(pos);
        Injector injector = HomeCampConstants.getInjector();
        if (injector.getInstance(CampfireSpawnEligibility.class).canRespawnAtCampfire(state)) {
            Optional<Vec3> campfireRespawnPosition = injector.getInstance(SafePosition.class).findBy(EntityType.PLAYER, world, pos);
            infoReturnable.setReturnValue(campfireRespawnPosition);
        }
    }
}
