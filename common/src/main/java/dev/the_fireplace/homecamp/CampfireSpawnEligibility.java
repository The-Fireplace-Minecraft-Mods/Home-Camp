package dev.the_fireplace.homecamp;

import dev.the_fireplace.homecamp.domain.config.ConfigValues;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;

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
        return state.is(Blocks.SOUL_CAMPFIRE) || (!configValues.isSoulCampfiresOnly() && state.is(Blocks.CAMPFIRE));
    }
}
