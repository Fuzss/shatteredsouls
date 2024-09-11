package fuzs.shatteredsouls.neoforge.data.client;

import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.neoforge.api.data.v2.client.AbstractParticleDescriptionProvider;
import fuzs.puzzleslib.neoforge.api.data.v2.core.NeoForgeDataProviderContext;
import fuzs.shatteredsouls.client.particle.BloodParticle;

public class ModParticleProvider extends AbstractParticleDescriptionProvider {

    public ModParticleProvider(NeoForgeDataProviderContext context) {
        super(context);
    }

    @Override
    public void addParticleDescriptions() {
        this.add(BloodParticle.BLOOD_PARTICLE_TYPE, ResourceLocationHelper.withDefaultNamespace("splash"), 0, 3);
    }
}
