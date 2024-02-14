package fuzs.shatteredsouls.mixin.client.accessor;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AgeableListModel.class)
public interface AgeableListModelAccessor {

    @Invoker("headParts")
    Iterable<ModelPart> shatteredsouls$callHeadParts();

    @Invoker("bodyParts")
    Iterable<ModelPart> shatteredsouls$callBodyParts();

    @Accessor("scaleHead")
    boolean shatteredsouls$getScaleHead();
    
    @Accessor("babyYHeadOffset")
    float shatteredsouls$getBabyYHeadOffset();
    
    @Accessor("babyZHeadOffset")
    float shatteredsouls$getBabyZHeadOffset();
    
    @Accessor("babyHeadScale")
    float shatteredsouls$getBabyHeadScale();
    
    @Accessor("babyBodyScale")
    float shatteredsouls$getBabyBodyScale();
    
    @Accessor("bodyYOffset")
    float shatteredsouls$getBodyYOffset();
}
