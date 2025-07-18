package fuzs.shatteredsouls.client.model.geom;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import fuzs.puzzleslib.api.client.renderer.v1.RenderPropertyKey;
import fuzs.shatteredsouls.client.handler.ShatterRenderHandler;
import fuzs.shatteredsouls.mixin.client.accessor.ModelPartAccessor;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ARGB;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;
import java.util.Set;

public class EntityModelAdapter {
    static final RandomSource RANDOM = RandomSource.create();

    private final LivingEntityRenderer<?, ?, ?> renderer;
    private final Collection<ModelPart> modelParts;

    public EntityModelAdapter(LivingEntityRenderer<?, ?, ?> renderer, Collection<ModelPart> modelParts) {
        this.renderer = renderer;
        // store current poses for all model parts; a lot of models do not set every model property for the currently rendering entity,
        // meaning we are likely to change a value which is not going to be reset for the next actual entity render
        // all the original poses are restored at the end of our operation
        this.modelParts = modelParts;
    }

    @SuppressWarnings("unchecked")
    public <T extends LivingEntityRenderState, M extends EntityModel<T>> void setupModelParts(T entityRenderState, float partialTick) {
        // we need to call these methods as they also handle things like model part visibility
        // we don't care about the rotations, those are reset directly afterward
        ((M) this.renderer.getModel()).setupAnim(entityRenderState);
        this.modelParts.forEach(ModelPart::resetPose);
    }

    public void setupAndRenderModelParts(LivingEntityRenderState entityRenderState, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, float animationProgress) {

        setupAndRenderModelParts(this.modelParts,
                entityRenderState,
                poseStack,
                vertexConsumer,
                packedLight,
                animationProgress);
    }

    protected static void setupAndRenderModelParts(Collection<ModelPart> modelParts, LivingEntityRenderState entityRenderState, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, float animationProgress) {

        ModelPart[] parts = explodeModelParts(modelParts).toArray(ModelPart[]::new);
        Vec3 deltaMovement = RenderPropertyKey.getOrDefault(entityRenderState,
                ShatterRenderHandler.DELTA_MOVEMENT_RENDER_PROPERTY_KEY,
                Vec3.ZERO);
        int entityId = RenderPropertyKey.getOrDefault(entityRenderState,
                ShatterRenderHandler.ENTITY_ID_RENDER_PROPERTY_KEY,
                0);
        float alpha = 1.0F - animationProgress;
        RandomSource random = RANDOM;

        for (int i = 0; i < parts.length; i++) {

            poseStack.pushPose();

            random.setSeed((long) random.nextInt() * entityId * i * 1000);
            setupRotations(poseStack, animationProgress, deltaMovement, random);

            ModelPart modelPart = parts[i];
            modelPart.translateAndRotate(poseStack);
            int color = ARGB.colorFromFloat(alpha, 1.0F, 1.0F, 1.0F);
            ModelPartAccessor.class.cast(modelPart)
                    .shatteredsouls$callCompile(poseStack.last(),
                            vertexConsumer,
                            packedLight,
                            OverlayTexture.NO_OVERLAY,
                            color);

            poseStack.popPose();
        }
    }

    private static Collection<ModelPart> explodeModelParts(Collection<ModelPart> modelParts) {

        // it is important that parents generally come before children,
        // which should work fine by using identity set since the order is related to the initialization order of the instances (?)
        // ...well at least it does work in-game and this seems a valid reason why it does haha
        Set<ModelPart> explodedModelParts = Sets.newIdentityHashSet();
        modelParts.forEach((ModelPart modelPart) -> {
            explodeModelPartAndChildren(modelPart, explodedModelParts);
        });
        explodedModelParts.forEach((ModelPart modelPart) -> {
            if (!modelPart.isEmpty()) {
                modelPart.yRot -= 12.0F;
            }
        });

        return explodedModelParts;
    }

    private static void explodeModelPartAndChildren(ModelPart modelPart, Collection<ModelPart> modelParts) {

        if (!modelPart.visible || !modelParts.add(modelPart)) return;

        for (ModelPart childModelPart : ModelPartAccessor.class.cast(modelPart).shatteredsouls$getChildren().values()) {

            if (!childModelPart.isEmpty()) {
                childModelPart.x += modelPart.x;
                childModelPart.y += modelPart.y;
                childModelPart.z += modelPart.z;
                childModelPart.xRot += modelPart.xRot;
                childModelPart.yRot += modelPart.yRot;
                childModelPart.zRot += modelPart.zRot;
                childModelPart.xScale *= modelPart.xScale;
                childModelPart.yScale *= modelPart.yScale;
                childModelPart.zScale *= modelPart.zScale;
            }

            explodeModelPartAndChildren(childModelPart, modelParts);
        }
    }

    private static void setupRotations(PoseStack poseStack, float animationProgress, Vec3 deltaMovement, RandomSource random) {

        double offsetX =
                random.nextFloat() * (random.nextFloat() > 0.5F ? -1.0 : 1.0) * animationProgress * deltaMovement.z *
                        5.0;
        double offsetY = random.nextDouble() * animationProgress * (deltaMovement.y + (random.nextDouble() - 1.0));
        double offsetZ =
                random.nextFloat() * (random.nextFloat() > 0.5F ? -1.0 : 1.0) * animationProgress * deltaMovement.x *
                        5.0;
        poseStack.translate(offsetX, offsetY, offsetZ);

        float rotationBase = 180.0F * random.nextFloat() * animationProgress;
        float rotationX = random.nextFloat() * (random.nextBoolean() ? -1.0F : 1.0F) * animationProgress;
        poseStack.mulPose(Axis.XP.rotationDegrees(rotationBase * rotationX));
        float rotationY = random.nextFloat() * (random.nextBoolean() ? -1.0F : 1.0F) * animationProgress;
        poseStack.mulPose(Axis.YP.rotationDegrees(rotationBase * rotationY));
        float rotationZ = random.nextFloat() * (random.nextBoolean() ? -1.0F : 1.0F) * animationProgress;
        poseStack.mulPose(Axis.ZP.rotationDegrees(rotationBase * rotationZ));
    }
}
