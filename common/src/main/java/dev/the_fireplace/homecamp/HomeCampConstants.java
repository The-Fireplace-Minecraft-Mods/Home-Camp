package dev.the_fireplace.homecamp;

import com.google.inject.Injector;
import dev.the_fireplace.annotateddi.api.Injectors;

public final class HomeCampConstants
{
    public static final String MODID = "homecamp";

    public static Injector getInjector() {
        return Injectors.INSTANCE.getAutoInjector(MODID);
    }
}
