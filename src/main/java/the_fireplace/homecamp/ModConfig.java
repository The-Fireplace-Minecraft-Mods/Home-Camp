package the_fireplace.homecamp;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = HomeCamp.MODID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean soulCampfiresOnly = false;
    @ConfigEntry.Gui.Tooltip
    public boolean extinguishOnSpawn = true;
    @ConfigEntry.Gui.Tooltip
    public boolean requireLitCampfire = true;
}
