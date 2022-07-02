package dev.the_fireplace.homecamp.entrypoints;

import com.google.inject.Injector;
import dev.the_fireplace.homecamp.HomeCampConstants;
import dev.the_fireplace.homecamp.eventhandlers.ConfigGuiRegistrationHandler;
import dev.the_fireplace.homecamp.eventhandlers.UseBlockHandler;
import dev.the_fireplace.lib.api.events.FLEventBus;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.FMLNetworkConstants;

@Mod("homecamp")
public final class Forge
{
    public Forge() {
        Injector injector = HomeCampConstants.getInjector();
        MinecraftForge.EVENT_BUS.register(new UseBlockHandler());
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            FLEventBus.BUS.register(injector.getInstance(ConfigGuiRegistrationHandler.class));
            return null;
        });

        // Register as optional on both sides
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }
}
