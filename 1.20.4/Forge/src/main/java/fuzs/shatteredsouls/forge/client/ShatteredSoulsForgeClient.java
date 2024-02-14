package fuzs.shatteredsouls.forge.client;

import fuzs.shatteredsouls.ShatteredSouls;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.shatteredsouls.client.ShatteredSoulsClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = ShatteredSouls.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ShatteredSoulsForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(ShatteredSouls.MOD_ID, ShatteredSoulsClient::new);
    }
}
