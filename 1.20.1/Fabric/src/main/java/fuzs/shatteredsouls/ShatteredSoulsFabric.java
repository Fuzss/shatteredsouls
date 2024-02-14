package fuzs.shatteredsouls;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class ShatteredSoulsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(ShatteredSouls.MOD_ID, ShatteredSouls::new);
    }
}
