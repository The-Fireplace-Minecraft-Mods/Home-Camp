package dev.the_fireplace.homecamp.eventhandlers;

import com.google.inject.Injector;
import dev.the_fireplace.homecamp.CampfireSpawnEligibility;
import dev.the_fireplace.homecamp.HomeCampConstants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class UseBlockHandler
{
    @SubscribeEvent
    public void onUseBlock(PlayerInteractEvent.RightClickBlock event) {
        Injector injector = HomeCampConstants.getInjector();
        Level world = event.getWorld();
        BlockHitResult hitResult = event.getHitVec();
        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();

        BlockState state = world.getBlockState(hitResult.getBlockPos());
        if (!player.level.isClientSide()
            && player.isShiftKeyDown()
            && injector.getInstance(CampfireSpawnEligibility.class).canRespawnAtCampfire(state)
            && player.getItemInHand(hand).isEmpty()
            && player instanceof ServerPlayer
        ) {
            ((ServerPlayer) player).setRespawnPosition(world.dimension(), hitResult.getBlockPos(), player.getYRot(), true, true);
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }
}
