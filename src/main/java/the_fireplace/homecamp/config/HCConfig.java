package the_fireplace.homecamp.config;

import dev.the_fireplace.annotateddi.api.di.Implementation;
import dev.the_fireplace.lib.api.io.interfaces.access.StorageReadBuffer;
import dev.the_fireplace.lib.api.io.interfaces.access.StorageWriteBuffer;
import dev.the_fireplace.lib.api.lazyio.injectables.ConfigStateManager;
import dev.the_fireplace.lib.api.lazyio.interfaces.Config;
import the_fireplace.homecamp.HomeCamp;
import the_fireplace.homecamp.domain.config.ConfigValues;

import javax.inject.Inject;
import javax.inject.Named;

@Implementation("the_fireplace.homecamp.domain.config.ConfigValues")
public final class HCConfig implements ConfigValues, Config {
    private boolean soulCampfiresOnly;
    private boolean extinguishOnSpawn;
    private boolean requireLitCampfire;

    private final ConfigValues defaultConfig;

    @Inject
    public HCConfig(ConfigStateManager configStateManager, @Named("default") ConfigValues defaultConfig) {
        this.defaultConfig = defaultConfig;
        configStateManager.initialize(this);
    }

    @Override
    public void readFrom(StorageReadBuffer buffer) {
        soulCampfiresOnly = buffer.readBool("soulCampfiresOnly", defaultConfig.isSoulCampfiresOnly());
        extinguishOnSpawn = buffer.readBool("extinguishOnSpawn", defaultConfig.isExtinguishOnSpawn());
        requireLitCampfire = buffer.readBool("requireLitCampfire", defaultConfig.isRequireLitCampfire());
    }

    @Override
    public void writeTo(StorageWriteBuffer buffer) {
        buffer.writeBool("soulCampfiresOnly", soulCampfiresOnly);
        buffer.writeBool("extinguishOnSpawn", extinguishOnSpawn);
        buffer.writeBool("requireLitCampfire", requireLitCampfire);
    }

    @Override
    public boolean isSoulCampfiresOnly() {
        return soulCampfiresOnly;
    }

    public void setSoulCampfiresOnly(boolean soulCampfiresOnly) {
        this.soulCampfiresOnly = soulCampfiresOnly;
    }

    @Override
    public boolean isExtinguishOnSpawn() {
        return extinguishOnSpawn;
    }

    public void setExtinguishOnSpawn(boolean extinguishOnSpawn) {
        this.extinguishOnSpawn = extinguishOnSpawn;
    }

    @Override
    public boolean isRequireLitCampfire() {
        return requireLitCampfire;
    }

    public void setRequireLitCampfire(boolean requireLitCampfire) {
        this.requireLitCampfire = requireLitCampfire;
    }

    @Override
    public String getId() {
        return HomeCamp.MODID;
    }
}
