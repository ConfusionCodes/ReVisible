package me.confusioncodes.revisible.mixin;

import me.confusioncodes.revisible.ReVisible;
import net.minecraft.client.texture.Animator;
import net.minecraft.client.texture.SpriteContents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BooleanSupplier;

@Mixin(targets = "net.minecraft.client.texture.SpriteContents$Animation")
public abstract class AnimatedTextureMixin {

    @Shadow(aliases = "field_28469") @Final SpriteContents this$0;
    
    @Shadow abstract void upload(int x, int y, int frame);

    @Inject(method = "createAnimator", at = @At("HEAD"), cancellable = true)
    private void revisible$customAnimator(CallbackInfoReturnable<Animator> cir) {
        final Identifier name = this$0.getId();
        if(name.equals(ReVisible.BARRIER_ID))
            cir.setReturnValue(createAnimator(() -> ReVisible.showBarriers));
        if(ReVisible.LIGHT_IDS.contains(name))
            cir.setReturnValue(createAnimator(() -> ReVisible.showLights));
        if(name.equals(ReVisible.STRUCTURE_VOID_ID))
            cir.setReturnValue(createAnimator(() -> ReVisible.showStructureVoid));
    }
    
    @Unique
    private Animator createAnimator(BooleanSupplier show) {
        return new Animator() {
            private boolean showing = false;

            @Override
            public void tick(int x, int y) {
                if(showing != show.getAsBoolean()) {
                    showing = show.getAsBoolean();
                    upload(x, y, showing ? 1 : 0);
                }
            }

            @Override
            public void close() {}
        };
    }
}
