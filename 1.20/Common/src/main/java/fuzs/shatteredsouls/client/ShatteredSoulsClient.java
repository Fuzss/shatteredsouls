package fuzs.shatteredsouls.client;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.ParticleProvidersContext;
import fuzs.puzzleslib.api.client.event.v1.RenderLivingEvents;
import fuzs.puzzleslib.api.event.v1.entity.living.LivingDeathCallback;
import fuzs.puzzleslib.api.event.v1.entity.living.LivingEvents;
import fuzs.shatteredsouls.client.handler.MobDismembermentHandler;
import fuzs.shatteredsouls.client.handler.ShatterRenderHandler;
import fuzs.shatteredsouls.client.handler.ShatterTickHandler;
import fuzs.shatteredsouls.client.particle.BloodParticle;

public class ShatteredSoulsClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerHandlers();
    }

    private static void registerHandlers() {
        LivingDeathCallback.EVENT.register(ShatterTickHandler::onLivingDeath);
        LivingEvents.TICK.register(ShatterTickHandler::onLivingTick);
        RenderLivingEvents.BEFORE.register(ShatterRenderHandler::onBeforeRenderEntity);
        LivingDeathCallback.EVENT.register(MobDismembermentHandler::onLivingDeath);
    }

    @Override
    public void onRegisterParticleProviders(ParticleProvidersContext context) {
        context.registerClientParticleProvider(BloodParticle.BLOOD_PARTICLE_TYPE, BloodParticle.Provider::new);
    }
}
