package dev.the_fireplace.homecamp.config;

import dev.the_fireplace.homecamp.HomeCampConstants;
import dev.the_fireplace.homecamp.domain.config.ConfigValues;
import dev.the_fireplace.lib.api.chat.injectables.TranslatorFactory;
import dev.the_fireplace.lib.api.chat.interfaces.Translator;
import dev.the_fireplace.lib.api.client.injectables.ConfigScreenBuilderFactory;
import dev.the_fireplace.lib.api.client.interfaces.ConfigScreenBuilder;
import dev.the_fireplace.lib.api.lazyio.injectables.ConfigStateManager;
import net.minecraft.client.gui.screens.Screen;

import javax.inject.Inject;
import javax.inject.Named;

public final class HCConfigScreenFactory
{
    private static final String TRANSLATION_BASE = "text.config." + HomeCampConstants.MODID + ".";
    private static final String OPTION_TRANSLATION_BASE = TRANSLATION_BASE + "option.";

    private final Translator translator;
    private final ConfigStateManager configStateManager;
    private final HCConfig config;
    private final ConfigValues defaultConfigValues;
    private final ConfigScreenBuilderFactory configScreenBuilderFactory;

    private ConfigScreenBuilder configScreenBuilder;

    @Inject
    public HCConfigScreenFactory(
        TranslatorFactory translatorFactory,
        ConfigStateManager configStateManager,
        HCConfig config,
        @Named("default") ConfigValues defaultConfigValues,
        ConfigScreenBuilderFactory configScreenBuilderFactory
    ) {
        this.translator = translatorFactory.getTranslator(HomeCampConstants.MODID);
        this.configStateManager = configStateManager;
        this.config = config;
        this.defaultConfigValues = defaultConfigValues;
        this.configScreenBuilderFactory = configScreenBuilderFactory;
    }

    public Screen getConfigScreen(Screen parent) {
        this.configScreenBuilder = configScreenBuilderFactory.create(
            translator,
            TRANSLATION_BASE + "title",
            TRANSLATION_BASE + "general",
            parent,
            () -> configStateManager.save(config)
        ).get();
        addGeneralCategoryEntries();

        return this.configScreenBuilder.build();
    }

    private void addGeneralCategoryEntries() {
        configScreenBuilder.addBoolToggle(
            OPTION_TRANSLATION_BASE + "soulCampfiresOnly",
            config.isSoulCampfiresOnly(),
            defaultConfigValues.isSoulCampfiresOnly(),
            config::setSoulCampfiresOnly
        );
        configScreenBuilder.addBoolToggle(
            OPTION_TRANSLATION_BASE + "extinguishOnSpawn",
            config.isExtinguishOnSpawn(),
            defaultConfigValues.isExtinguishOnSpawn(),
            config::setExtinguishOnSpawn
        );
        configScreenBuilder.addBoolToggle(
            OPTION_TRANSLATION_BASE + "requireLitCampfire",
            config.isRequireLitCampfire(),
            defaultConfigValues.isRequireLitCampfire(),
            config::setRequireLitCampfire
        );
        configScreenBuilder.addIntSlider(
            OPTION_TRANSLATION_BASE + "mobsNoSpawnRegion",
            config.noMobSpawnRegion(),
            defaultConfigValues.noMobSpawnRegion(),
            config::setNoMobSpawnRegion,
            0,
            256
        );
    }
}