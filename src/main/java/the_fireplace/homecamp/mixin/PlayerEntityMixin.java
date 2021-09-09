package the_fireplace.homecamp.mixin;

import dev.the_fireplace.annotateddi.api.DIContainer;
import dev.the_fireplace.lib.api.teleport.injectables.SafePosition;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
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
import the_fireplace.homecamp.HomeCamp;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Inject(at = @At("HEAD"), method = "findRespawnPosition", cancellable = true)
	private static void findRespawnPosition(ServerWorld world, BlockPos pos, float f, boolean bl, boolean bl2, CallbackInfoReturnable<Optional<Vec3d>> infoReturnable) {
		BlockState state = world.getBlockState(pos);
		if (CampfireSpawnEligibility.canRespawnAtCampfire(state)) {
			if (HomeCamp.config.extinguishOnSpawn) {
				extinguishCampfire(world, pos, state);
			}
			Optional<Vec3d> campfireRespawnPosition = DIContainer.get().getInstance(SafePosition.class).findBy(EntityType.PLAYER, world, pos);
			infoReturnable.setReturnValue(campfireRespawnPosition);
		}
	}

	private static void extinguishCampfire(ServerWorld world, BlockPos pos, BlockState state) {
		world.syncWorldEvent(null, 1009, pos, 0);
		CampfireBlock.extinguish(world, pos, state);
		world.setBlockState(pos, state.with(CampfireBlock.LIT, false));
	}
}
