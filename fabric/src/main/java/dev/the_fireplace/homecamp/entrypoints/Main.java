package dev.the_fireplace.homecamp.entrypoints;

import com.google.inject.Injector;
import dev.the_fireplace.homecamp.CampfireSpawnEligibility;
import dev.the_fireplace.homecamp.HomeCampConstants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.BlockState;

public final class Main implements ModInitializer
{
    @Override
    public void onInitialize() {
        Injector injector = HomeCampConstants.getInjector();
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockState state = world.getBlockState(hitResult.getBlockPos());
            if (!player.level.isClientSide()
                && player.isShiftKeyDown()
                && injector.getInstance(CampfireSpawnEligibility.class).canRespawnAtCampfire(state)
                && player.getItemInHand(hand).isEmpty()
                && player instanceof ServerPlayer
            ) {
                ((ServerPlayer) player).setRespawnPosition(world.dimension(), hitResult.getBlockPos(), player.getYRot(), true, true);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });
	}
}
