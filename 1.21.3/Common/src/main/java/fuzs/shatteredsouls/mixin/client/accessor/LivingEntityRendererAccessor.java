package fuzs.shatteredsouls.mixin.client.accessor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntityRenderer.class)
public interface LivingEntityRendererAccessor<T extends LivingEntity, M extends EntityModel<T>> {

    @Invoker("scale")
    void shatteredsouls$callScale(T livingEntity, PoseStack matrixStack, float partialTickTime);

    @Invoker("getBob")
    float shatteredsouls$callGetBob(T livingBase, float partialTick);
}
