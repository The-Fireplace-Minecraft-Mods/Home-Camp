package the_fireplace.homecamp.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import the_fireplace.homecamp.HomeCamp;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Inject(at = @At(value="HEAD"), method = "findRespawnPosition", cancellable = true)
	private static void findRespawnPosition(ServerWorld world, BlockPos pos, float f, boolean bl, boolean bl2, CallbackInfoReturnable<Optional<Vec3d>> infoReturnable) {
		BlockState state = world.getBlockState(pos);
	    if((state.isOf(Blocks.SOUL_CAMPFIRE) || (HomeCamp.config.allowRegularCampfires && state.isOf(Blocks.CAMPFIRE)))
			&& (!HomeCamp.config.requireLitCampfire || CampfireBlock.isLitCampfire(state))) {
	    	if(HomeCamp.config.extinguishOnSpawn) {
				world.syncWorldEvent(null, 1009, pos, 0);
				CampfireBlock.extinguish(world, pos, state);
				world.setBlockState(pos, state.with(CampfireBlock.LIT, false));
			}
			infoReturnable.setReturnValue(RespawnAnchorBlock.findRespawnPosition(EntityType.PLAYER, world, pos));
		}
	}
}
