package me.confusioncodes.revisible.mixin;

import net.minecraft.block.*;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BarrierBlock.class)
public abstract class BarrierBlockMixin extends Block implements Waterloggable {
    public BarrierBlockMixin(Settings settings) {
        super(settings);
    }

    /**
     * @author ConfusionCodes
     * @reason Barriers have the BlockRenderType of 'INVISIBLE' by default, causing them to ignore the associated model.
     */
    @Overwrite
    protected @NotNull BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }
    
    @Unique
    @Override
    protected boolean isSideInvisible(BlockState blockState, BlockState blockState2, Direction direction) {
        if(blockState2.isOf((BarrierBlock)(Object)this)) {
            return true;
        }
        return super.isSideInvisible(blockState, blockState2, direction);
    }
}
