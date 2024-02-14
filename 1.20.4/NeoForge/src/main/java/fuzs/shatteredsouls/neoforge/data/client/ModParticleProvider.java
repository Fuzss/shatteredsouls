package fuzs.shatteredsouls.neoforge.data.client;

import fuzs.puzzleslib.neoforge.api.data.v2.client.AbstractParticleDescriptionProvider;
import fuzs.puzzleslib.neoforge.api.data.v2.core.ForgeDataProviderContext;
import fuzs.shatteredsouls.client.particle.BloodParticle;
import net.minecraft.resources.ResourceLocation;

public class ModParticleProvider extends AbstractParticleDescriptionProvider {

    public ModParticleProvider(ForgeDataProviderContext context) {
        super(context);
    }

    @Override
    public void addParticleDescriptions() {
        this.add(BloodParticle.BLOOD_PARTICLE_TYPE, new ResourceLocation("splash"), 0, 3);
    }
}
