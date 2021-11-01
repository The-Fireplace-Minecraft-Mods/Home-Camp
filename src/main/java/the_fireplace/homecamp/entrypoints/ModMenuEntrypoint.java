package the_fireplace.homecamp.entrypoints;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.the_fireplace.annotateddi.api.DIContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import the_fireplace.homecamp.config.HCConfigScreenFactory;

@Environment(EnvType.CLIENT)
public final class ModMenuEntrypoint implements ModMenuApi
{
    private final HCConfigScreenFactory configScreenFactory = DIContainer.get().getInstance(HCConfigScreenFactory.class);

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return configScreenFactory::getConfigScreen;
    }
}
