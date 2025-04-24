package fuzs.shatteredsouls.fabric;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.shatteredsouls.ShatteredSouls;
import net.fabricmc.api.ModInitializer;

public class ShatteredSoulsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(ShatteredSouls.MOD_ID, ShatteredSouls::new);
    }
}
