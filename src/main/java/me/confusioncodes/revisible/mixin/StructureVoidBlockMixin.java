package me.confusioncodes.revisible.mixin;

import net.minecraft.block.*;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(StructureVoidBlock.class)
public class StructureVoidBlockMixin extends Block {
    
    public StructureVoidBlockMixin(Settings settings) {
        super(settings);
    }
    /**
     * @author ConfusionCodes
     * @reason Structure void blocks have the BlockRenderType of 'INVISIBLE' by default, causing them to ignore the associated model.
     */
    @Overwrite
    protected @NotNull BlockRenderType getRenderType(net.minecraft.block.BlockState blockState) {
        return BlockRenderType.MODEL;
    }
    
    /**
     * @author ConfusionCodes
     * @reason Structure blocks are annoying to hit. This changes the shape to a regular block.
     */
    @Overwrite
    protected @NotNull VoxelShape getOutlineShape(BlockState blockState, BlockView world, BlockPos blockPos, ShapeContext context) {
        return context.isHolding(Items.STRUCTURE_VOID) ? VoxelShapes.fullCube() : VoxelShapes.empty();
    }

    @Unique
    @Override
    protected boolean isSideInvisible(BlockState blockState, BlockState blockState2, net.minecraft.util.math.Direction direction) {
        if(blockState2.isOf((StructureVoidBlock)(Object)this)) {
            return true;
        }
        return super.isSideInvisible(blockState, blockState2, direction);
    }
    
}
