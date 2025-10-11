package fuzs.shatteredsouls.fabric.client;

import fuzs.shatteredsouls.ShatteredSouls;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.shatteredsouls.client.ShatteredSoulsClient;
import net.fabricmc.api.ClientModInitializer;

public class ShatteredSoulsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(ShatteredSouls.MOD_ID, ShatteredSoulsClient::new);
    }
}
