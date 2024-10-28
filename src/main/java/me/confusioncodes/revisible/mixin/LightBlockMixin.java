package me.confusioncodes.revisible.mixin;

import net.minecraft.block.*;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LightBlock.class)
public class LightBlockMixin extends Block implements Waterloggable {
    public LightBlockMixin(Settings settings) {
        super(settings);
    }
    
    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V"))
    private static void revisible$disableBlockBreakParticles(Args args) {
        Settings settings = args.get(0);
        args.set(0, settings.noBlockBreakParticles());
    }
    
    /**
     * @author ConfusionCodes
     * @reason Light blocks have the BlockRenderType of 'INVISIBLE' by default, causing them to ignore the associated model.
     */
    @Overwrite
    protected @NotNull BlockRenderType getRenderType(net.minecraft.block.BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Unique
    @Override
    protected boolean isSideInvisible(BlockState blockState, BlockState blockState2, Direction direction) {
        if(blockState2.isOf((LightBlock)(Object)this)) {
            return true;
        }
        return super.isSideInvisible(blockState, blockState2, direction);
    }
}
