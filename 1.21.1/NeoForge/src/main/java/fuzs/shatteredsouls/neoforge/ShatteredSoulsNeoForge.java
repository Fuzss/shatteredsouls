package fuzs.shatteredsouls.neoforge;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import fuzs.shatteredsouls.ShatteredSouls;
import fuzs.shatteredsouls.neoforge.data.client.ModParticleProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod(ShatteredSouls.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ShatteredSoulsNeoForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(ShatteredSouls.MOD_ID, ShatteredSouls::new);
        DataProviderHelper.registerDataProviders(ShatteredSouls.MOD_ID, ModParticleProvider::new);
    }
}
