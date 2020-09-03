package the_fireplace.homecamp;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = HomeCamp.MODID)
public class ModConfig implements ConfigData {
    public boolean allowRegularCampfires = true;
    public boolean extinguishOnSpawn = true;
    public boolean requireLitCampfire = true;
}
