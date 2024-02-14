package fuzs.shatteredsouls.mixin.client.accessor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(ModelPart.class)
public interface ModelPartAccessor {

    @Accessor("children")
    Map<String, ModelPart> shatteredsouls$getChildren();

    @Invoker("compile")
    void shatteredsouls$callCompile(PoseStack.Pose pose, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);
}
