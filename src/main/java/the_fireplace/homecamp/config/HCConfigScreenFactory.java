package the_fireplace.homecamp.config;

import dev.the_fireplace.lib.api.chat.injectables.TranslatorFactory;
import dev.the_fireplace.lib.api.chat.interfaces.Translator;
import dev.the_fireplace.lib.api.client.injectables.ConfigScreenBuilderFactory;
import dev.the_fireplace.lib.api.client.interfaces.ConfigScreenBuilder;
import dev.the_fireplace.lib.api.lazyio.injectables.ConfigStateManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import the_fireplace.homecamp.HomeCamp;
import the_fireplace.homecamp.domain.config.ConfigValues;

import javax.inject.Inject;
import javax.inject.Named;

@Environment(EnvType.CLIENT)
public final class HCConfigScreenFactory {
    private static final String TRANSLATION_BASE = "text.config." + HomeCamp.MODID + ".";
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
        this.translator = translatorFactory.getTranslator(HomeCamp.MODID);
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
        );
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
    }
}
