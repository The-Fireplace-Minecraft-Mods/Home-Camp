package dev.the_fireplace.homecamp.eventhandlers;

import dev.the_fireplace.homecamp.HomeCampConstants;
import dev.the_fireplace.homecamp.config.HCConfigScreenFactory;
import dev.the_fireplace.lib.api.events.ConfigScreenRegistration;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.inject.Inject;

public final class ConfigGuiRegistrationHandler
{
    private final HCConfigScreenFactory configScreenFactory;

    @Inject
    public ConfigGuiRegistrationHandler(HCConfigScreenFactory configScreenFactory) {
        this.configScreenFactory = configScreenFactory;
    }

    @SubscribeEvent
    public void registerConfigGui(ConfigScreenRegistration configScreenRegistration) {
        configScreenRegistration.getConfigGuiRegistry().register(HomeCampConstants.MODID, configScreenFactory::getConfigScreen);
    }
}