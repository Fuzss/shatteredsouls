package fuzs.shatteredsouls.neoforge.client;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import fuzs.shatteredsouls.ShatteredSouls;
import fuzs.shatteredsouls.client.ShatteredSoulsClient;
import fuzs.shatteredsouls.neoforge.data.client.ModParticleProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = ShatteredSouls.MOD_ID, dist = Dist.CLIENT)
public class ShatteredSoulsNeoForgeClient {

    public ShatteredSoulsNeoForgeClient() {
        ClientModConstructor.construct(ShatteredSouls.MOD_ID, ShatteredSoulsClient::new);
        DataProviderHelper.registerDataProviders(ShatteredSouls.MOD_ID, ModParticleProvider::new);
    }
}
