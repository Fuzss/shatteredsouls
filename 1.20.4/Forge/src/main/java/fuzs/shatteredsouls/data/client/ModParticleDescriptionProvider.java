package fuzs.shatteredsouls.data.client;

import fuzs.puzzleslib.api.data.v2.client.AbstractParticleDescriptionProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.core.ForgeDataProviderContext;
import fuzs.shatteredsouls.client.particle.BloodParticle;
import net.minecraft.resources.ResourceLocation;

public class ModParticleDescriptionProvider extends AbstractParticleDescriptionProvider {

    public ModParticleDescriptionProvider(DataProviderContext context) {
        super((ForgeDataProviderContext) context);
    }

    @Override
    protected void addParticleDescriptions() {
        this.add(BloodParticle.BLOOD_PARTICLE_TYPE, new ResourceLocation("splash"), 0, 3);
    }
}
