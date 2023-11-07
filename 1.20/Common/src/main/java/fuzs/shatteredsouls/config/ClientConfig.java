package fuzs.shatteredsouls.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;
import fuzs.puzzleslib.api.config.v3.serialization.ConfigDataSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;

import java.util.List;

public class ClientConfig implements ConfigCore {
    @Config(name = "shatter_animation_blacklist", description = "Mobs that shall not use the new death animation, sticking to their vanilla behavior.")
    List<String> shatterAnimationBlacklistRaw = ConfigDataSet.toString(Registries.ENTITY_TYPE, EntityType.ENDER_DRAGON);
    @Config(description = "Defeating a mob will spray out a bunch of blood particles.")
    public boolean bloodParticles = false;
    @Config(name = "blood_particles_blacklist", description = "Mobs that do not create blood particles after being defeated.")
    List<String> bloodParticlesBlacklistRaw = ConfigDataSet.toString(Registries.ENTITY_TYPE);

    public ConfigDataSet<EntityType<?>> shatterAnimationBlacklist;
    public ConfigDataSet<EntityType<?>> bloodParticleBlacklist;

    @Override
    public void afterConfigReload() {
        this.shatterAnimationBlacklist = ConfigDataSet.from(Registries.ENTITY_TYPE, this.shatterAnimationBlacklistRaw);
        this.bloodParticleBlacklist = ConfigDataSet.from(Registries.ENTITY_TYPE, this.bloodParticlesBlacklistRaw);
    }
}
