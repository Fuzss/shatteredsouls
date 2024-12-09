package fuzs.shatteredsouls.client.model.geom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

import java.util.Collection;

public class AgeableEntityModelAdapter extends EntityModelAdapter {
    private final Collection<ModelPart> babyModelParts;

    public AgeableEntityModelAdapter(LivingEntityRenderer<?, ?, ?> renderer, Collection<ModelPart> modelParts, Collection<ModelPart> babyModelParts) {
        super(renderer, modelParts);
        this.babyModelParts = babyModelParts;
    }

    @Override
    public <T extends LivingEntityRenderState, M extends EntityModel<T>> void setupModelParts(T entityRenderState, float partialTick) {
        super.setupModelParts(entityRenderState, partialTick);
        this.babyModelParts.forEach(ModelPart::resetPose);
    }

    @Override
    public void setupAndRenderModelParts(LivingEntityRenderState entityRenderState, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, float animationProgress) {

        if (entityRenderState.isBaby) {

            setupAndRenderModelParts(this.babyModelParts,
                    entityRenderState,
                    poseStack,
                    vertexConsumer,
                    packedLight,
                    animationProgress);
        } else {

            super.setupAndRenderModelParts(entityRenderState,
                    poseStack,
                    vertexConsumer,
                    packedLight,
                    animationProgress);
        }
    }
}
