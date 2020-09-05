package the_fireplace.homecamp;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = HomeCamp.MODID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean soulCampfiresOnly = false;
    @ConfigEntry.Gui.Tooltip
    public boolean extinguishOnSpawn = true;
    @ConfigEntry.Gui.Tooltip
    public boolean requireLitCampfire = true;
}
