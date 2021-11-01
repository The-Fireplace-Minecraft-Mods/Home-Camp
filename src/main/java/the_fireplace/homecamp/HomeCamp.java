package the_fireplace.homecamp;

import com.google.inject.Injector;
import dev.the_fireplace.annotateddi.api.entrypoints.DIModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public final class HomeCamp implements DIModInitializer
{
	public static final String MODID = "homecamp";

	@Override
	public void onInitialize(Injector diContainer) {
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			BlockState state = world.getBlockState(hitResult.getBlockPos());
			if (!player.world.isClient()
				&& player.isSneaking()
				&& diContainer.getInstance(CampfireSpawnEligibility.class).canRespawnAtCampfire(state)
				&& player.getStackInHand(hand).isEmpty()
				&& player instanceof ServerPlayerEntity
			) {
				((ServerPlayerEntity) player).setSpawnPoint(world.getRegistryKey(), hitResult.getBlockPos(), player.getYaw(), true, true);
				return ActionResult.SUCCESS;
			}
			return ActionResult.PASS;
		});
	}
}
