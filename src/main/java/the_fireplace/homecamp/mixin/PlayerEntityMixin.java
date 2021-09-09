package the_fireplace.homecamp.mixin;

import dev.the_fireplace.annotateddi.api.DIContainer;
import dev.the_fireplace.lib.api.teleport.injectables.SafePosition;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import the_fireplace.homecamp.CampfireSpawnEligibility;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public final class PlayerEntityMixin {
	@Inject(at = @At("HEAD"), method = "findRespawnPosition", cancellable = true)
	private static void findRespawnPosition(ServerWorld world, BlockPos pos, float f, boolean bl, boolean bl2, CallbackInfoReturnable<Optional<Vec3d>> infoReturnable) {
		BlockState state = world.getBlockState(pos);
		if (DIContainer.get().getInstance(CampfireSpawnEligibility.class).canRespawnAtCampfire(state)) {
			Optional<Vec3d> campfireRespawnPosition = DIContainer.get().getInstance(SafePosition.class).findBy(EntityType.PLAYER, world, pos);
			infoReturnable.setReturnValue(campfireRespawnPosition);
		}
	}
}
