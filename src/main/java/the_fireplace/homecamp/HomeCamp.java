package the_fireplace.homecamp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public class HomeCamp implements ModInitializer {
	public static final String MODID = "homecamp";

	@Override
	public void onInitialize() {
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			BlockState state = world.getBlockState(hitResult.getBlockPos());
			if(!player.world.isClient() && player.isSneaking() && state.isOf(Blocks.SOUL_CAMPFIRE) && CampfireBlock.isLitCampfire(state) && player.getActiveItem().equals(ItemStack.EMPTY) && player instanceof ServerPlayerEntity) {
				((ServerPlayerEntity) player).setSpawnPoint(world.getRegistryKey(), hitResult.getBlockPos(), player.yaw, true, true);
				return ActionResult.SUCCESS;
			}
			return ActionResult.PASS;
		});
	}
}
