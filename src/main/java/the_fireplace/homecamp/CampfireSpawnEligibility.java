package the_fireplace.homecamp;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import the_fireplace.homecamp.domain.config.ConfigValues;

import javax.inject.Inject;

public final class CampfireSpawnEligibility
{
    private final ConfigValues configValues;

    @Inject
    public CampfireSpawnEligibility(ConfigValues configValues) {
        this.configValues = configValues;
    }

    public boolean canRespawnAtCampfire(BlockState state) {
        return isSpawnCampfire(state) && meetsLitCampfireRequirement(state);
    }

    private boolean meetsLitCampfireRequirement(BlockState state) {
        return !configValues.isRequireLitCampfire() || CampfireBlock.isLitCampfire(state);
    }

    private boolean isSpawnCampfire(BlockState state) {
        return state.isOf(Blocks.SOUL_CAMPFIRE) || (!configValues.isSoulCampfiresOnly() && state.isOf(Blocks.CAMPFIRE));
    }
}
