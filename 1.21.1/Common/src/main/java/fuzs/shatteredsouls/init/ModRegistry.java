package fuzs.shatteredsouls.init;

import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.shatteredsouls.ShatteredSouls;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.SimpleParticleType;

public class ModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(ShatteredSouls.MOD_ID);
    public static final Holder.Reference<SimpleParticleType> BLOOD_PARTICLE_TYPE = REGISTRIES.registerParticleType(
            "blood");

    public static void bootstrap() {
        // NO-OP
    }
}
