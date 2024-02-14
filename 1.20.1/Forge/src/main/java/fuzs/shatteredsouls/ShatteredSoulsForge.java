package fuzs.shatteredsouls;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.data.v2.core.DataProviderHelper;
import fuzs.shatteredsouls.data.client.ModParticleDescriptionProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(ShatteredSouls.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ShatteredSoulsForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(ShatteredSouls.MOD_ID, ShatteredSouls::new);
        DataProviderHelper.registerDataProviders(ShatteredSouls.MOD_ID, ModParticleDescriptionProvider::new);
    }
}
