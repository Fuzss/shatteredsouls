package fuzs.shatteredsouls.neoforge.client;

import fuzs.shatteredsouls.ShatteredSouls;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.shatteredsouls.client.ShatteredSoulsClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = ShatteredSouls.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ShatteredSoulsNeoForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(ShatteredSouls.MOD_ID, ShatteredSoulsClient::new);
    }
}
