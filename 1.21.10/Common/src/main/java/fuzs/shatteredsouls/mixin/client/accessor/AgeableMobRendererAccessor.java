package fuzs.shatteredsouls.mixin.client.accessor;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AgeableMobRenderer.class)
public interface AgeableMobRendererAccessor<T extends Mob, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {

    @Accessor("babyModel")
    M shatteredsoulds$getBabyModel();
}
