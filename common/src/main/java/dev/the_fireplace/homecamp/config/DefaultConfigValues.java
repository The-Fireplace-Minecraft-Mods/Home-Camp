package dev.the_fireplace.homecamp.config;

import dev.the_fireplace.annotateddi.api.di.Implementation;
import dev.the_fireplace.homecamp.domain.config.ConfigValues;

@Implementation(name = "default")
public final class DefaultConfigValues implements ConfigValues
{
    @Override
    public boolean isSoulCampfiresOnly() {
        return false;
    }

    @Override
    public boolean isExtinguishOnSpawn() {
        return true;
    }

    @Override
    public boolean isRequireLitCampfire() {
        return true;
    }

    @Override
    public int noMobSpawnRegion() {
        return 32;
    }
}
