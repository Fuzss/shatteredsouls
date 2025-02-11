package fuzs.shatteredsouls.init;

import fuzs.puzzleslib.api.attachment.v4.DataAttachmentRegistry;
import fuzs.puzzleslib.api.attachment.v4.DataAttachmentType;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.shatteredsouls.ShatteredSouls;
import fuzs.shatteredsouls.client.helper.ClientEntityData;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;

public class ModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(ShatteredSouls.MOD_ID);
    public static final Holder.Reference<SimpleParticleType> BLOOD_PARTICLE_TYPE = REGISTRIES.registerParticleType(
            "blood");

    public static final DataAttachmentType<Entity, ClientEntityData> CLIENT_DATA_ATTACHMENT_TYPE = DataAttachmentRegistry.<ClientEntityData>entityBuilder()
            .build(ShatteredSouls.id("client_data"));

    public static void bootstrap() {
        // NO-OP
    }
}
