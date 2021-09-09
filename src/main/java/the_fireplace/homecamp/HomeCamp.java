package the_fireplace.homecamp;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public class HomeCamp implements ModInitializer {
	public static final String MODID = "homecamp";
	public static ModConfig config;

	@Override
	public void onInitialize() {
		AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			BlockState state = world.getBlockState(hitResult.getBlockPos());
			if (!player.world.isClient()
				&& player.isSneaking()
				&& CampfireSpawnEligibility.canRespawnAtCampfire(state)
				&& player.getStackInHand(hand).isEmpty()
				&& player instanceof ServerPlayerEntity
			) {
				((ServerPlayerEntity) player).setSpawnPoint(world.getRegistryKey(), hitResult.getBlockPos(), player.yaw, true, true);
				return ActionResult.SUCCESS;
			}
			return ActionResult.PASS;
		});
	}
}
