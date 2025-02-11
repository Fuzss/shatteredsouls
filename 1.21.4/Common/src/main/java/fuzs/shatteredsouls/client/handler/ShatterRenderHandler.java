package fuzs.shatteredsouls.client.handler;

import com.google.common.collect.MapMaker;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.puzzleslib.api.client.renderer.v1.RenderPropertyKey;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import fuzs.shatteredsouls.ShatteredSouls;
import fuzs.shatteredsouls.client.helper.ClientEntityData;
import fuzs.shatteredsouls.client.model.geom.AgeableEntityModelAdapter;
import fuzs.shatteredsouls.client.model.geom.EntityModelAdapter;
import fuzs.shatteredsouls.config.ClientConfig;
import fuzs.shatteredsouls.mixin.client.accessor.AgeableMobRendererAccessor;
import fuzs.shatteredsouls.mixin.client.accessor.LivingEntityRendererAccessor;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Map;

public class ShatterRenderHandler {
    private static final Map<LivingEntityRenderer<?, ?, ?>, EntityModelAdapter> MODEL_ADAPTERS = new MapMaker().concurrencyLevel(
            1).weakKeys().makeMap();
    private static final RenderPropertyKey<Unit> SHATTER_ANIMATION_RENDER_PROPERTY_KEY = new RenderPropertyKey<>(
            ShatteredSouls.id("shatter_animation"));
    public static final RenderPropertyKey<Vec3> DELTA_MOVEMENT_RENDER_PROPERTY_KEY = new RenderPropertyKey<>(
            ShatteredSouls.id("delta_movement"));
    public static final RenderPropertyKey<Integer> ENTITY_ID_RENDER_PROPERTY_KEY = new RenderPropertyKey<>(
            ShatteredSouls.id("entity_id"));
    public static final RenderPropertyKey<Float> BODY_ROT_RENDER_PROPERTY_KEY = new RenderPropertyKey<>(ShatteredSouls.id(
            "body_rot"));

    public static void onExtractRenderState(Entity entity, EntityRenderState entityRenderState, float partialTick) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.isDeadOrDying() &&
                !ShatteredSouls.CONFIG.get(ClientConfig.class).shatterAnimationBlacklist.contains(entity.getType())) {
            // vanilla is very aggressive with syncing those shared flags, so we set them during rendering since they are used right after the entity is rendered
            // fire flag prevents rendering the fire overlay, mainly useful for undead mobs burning in the sun
            entityRenderState.displayFireAnimation = false;
            // invisibility flag prevents the mob shadow from rendering which is not desired for the death animation
            // unfortunately the entity hitbox (F3+B) also no longer renders, but that's how it is
            entityRenderState.isInvisible = true;
            RenderPropertyKey.setRenderProperty(entityRenderState,
                    SHATTER_ANIMATION_RENDER_PROPERTY_KEY,
                    Unit.INSTANCE);
            RenderPropertyKey.setRenderProperty(entityRenderState,
                    DELTA_MOVEMENT_RENDER_PROPERTY_KEY,
                    ClientEntityData.getDeltaMovement(livingEntity));
            RenderPropertyKey.setRenderProperty(entityRenderState, ENTITY_ID_RENDER_PROPERTY_KEY, livingEntity.getId());
            // do not lerp this value to prevent flickering
            RenderPropertyKey.setRenderProperty(entityRenderState,
                    BODY_ROT_RENDER_PROPERTY_KEY,
                    livingEntity.yBodyRotO);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> EventResult onBeforeRenderEntity(S entityRenderState, LivingEntityRenderer<T, S, M> entityRenderer, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        if (!RenderPropertyKey.containsRenderProperty(entityRenderState, SHATTER_ANIMATION_RENDER_PROPERTY_KEY)) {
            return EventResult.PASS;
        }

        poseStack.pushPose();

        // same setup as vanilla living entity renderer
        float scale = entityRenderState.scale;
        poseStack.scale(scale, scale, scale);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        ((LivingEntityRendererAccessor<T, S, M>) entityRenderer).shatteredsouls$callScale(entityRenderState, poseStack);
        poseStack.translate(0F, -1.501F, 0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(RenderPropertyKey.getRenderProperty(entityRenderState,
                BODY_ROT_RENDER_PROPERTY_KEY)));

        EntityModelAdapter modelPartRenderer = MODEL_ADAPTERS.computeIfAbsent(entityRenderer,
                ShatterRenderHandler::createModelAdapter);
        modelPartRenderer.setupModelParts(entityRenderState, partialTick);

        RenderType renderType = RenderType.entityTranslucent(entityRenderer.getTextureLocation(entityRenderState));
        // vanilla seems to stop rendering for very low alpha, so we cannot fade out properly
        float animationProgress = Mth.clamp(
                (entityRenderState.deathTime + partialTick) / ShatterTickHandler.SHATTER_DEATH_TIME, 0.0F, 1.0F);
        modelPartRenderer.setupAndRenderModelParts(entityRenderState,
                poseStack,
                bufferSource.getBuffer(renderType),
                packedLight,
                animationProgress);

        poseStack.popPose();

        return EventResult.INTERRUPT;
    }

    private static EntityModelAdapter createModelAdapter(LivingEntityRenderer<?, ?, ?> entityRenderer) {
        if (entityRenderer instanceof AgeableMobRenderer<?, ?, ?>) {
            List<ModelPart> modelParts = entityRenderer.getModel().allParts();
            List<ModelPart> babyModelParts = ((AgeableMobRendererAccessor<?, ?, ?>) entityRenderer).shatteredsoulds$getBabyModel()
                    .allParts();
            return new AgeableEntityModelAdapter(entityRenderer, modelParts, babyModelParts);
        } else {
            return new EntityModelAdapter(entityRenderer, entityRenderer.getModel().allParts());
        }
    }
}
