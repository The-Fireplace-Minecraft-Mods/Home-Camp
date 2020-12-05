package the_fireplace.homecamp;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;

public class CampfireSpawnEligibility {
    public static boolean canRespawnAtCampfire(BlockState state) {
        return isSpawnCampfire(state) && meetsLitCampfireRequirement(state);
    }

    private static boolean meetsLitCampfireRequirement(BlockState state) {
        return !HomeCamp.config.requireLitCampfire || CampfireBlock.isLitCampfire(state);
    }

    private static boolean isSpawnCampfire(BlockState state) {
        return state.isOf(Blocks.SOUL_CAMPFIRE) || (!HomeCamp.config.soulCampfiresOnly && state.isOf(Blocks.CAMPFIRE));
    }
}
