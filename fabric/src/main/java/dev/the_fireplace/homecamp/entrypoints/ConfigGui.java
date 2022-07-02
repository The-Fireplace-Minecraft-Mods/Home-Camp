package dev.the_fireplace.homecamp.entrypoints;

import dev.the_fireplace.homecamp.HomeCampConstants;
import dev.the_fireplace.homecamp.config.HCConfigScreenFactory;
import dev.the_fireplace.lib.api.client.entrypoints.ConfigGuiEntrypoint;
import dev.the_fireplace.lib.api.client.interfaces.ConfigGuiRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class ConfigGui implements ConfigGuiEntrypoint
{
    private final HCConfigScreenFactory configScreenFactory = HomeCampConstants.getInjector().getInstance(HCConfigScreenFactory.class);

    @Override
    public void registerConfigGuis(ConfigGuiRegistry configGuiRegistry) {
        configGuiRegistry.register(HomeCampConstants.MODID, configScreenFactory::getConfigScreen);
    }
}
