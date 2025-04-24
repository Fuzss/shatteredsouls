package fuzs.shatteredsouls.neoforge;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.shatteredsouls.ShatteredSouls;
import net.neoforged.fml.common.Mod;

@Mod(ShatteredSouls.MOD_ID)
public class ShatteredSoulsNeoForge {

    public ShatteredSoulsNeoForge() {
        ModConstructor.construct(ShatteredSouls.MOD_ID, ShatteredSouls::new);
    }
}
