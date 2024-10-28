package me.confusioncodes.revisible;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.Set;

public class ReVisible implements ModInitializer {
    public static final String MOD_ID = "revisible";

    public static final Identifier BARRIER_ID = Identifier.of(MOD_ID, "block/barrier");
    public static boolean showBarriers = false;
    public static final Set<Identifier> LIGHT_IDS = Set.of(
            Identifier.of(MOD_ID, "block/light_00"),
            Identifier.of(MOD_ID, "block/light_01"),
            Identifier.of(MOD_ID, "block/light_02"),
            Identifier.of(MOD_ID, "block/light_03"),
            Identifier.of(MOD_ID, "block/light_04"),
            Identifier.of(MOD_ID, "block/light_05"),
            Identifier.of(MOD_ID, "block/light_06"),
            Identifier.of(MOD_ID, "block/light_07"),
            Identifier.of(MOD_ID, "block/light_08"),
            Identifier.of(MOD_ID, "block/light_09"),
            Identifier.of(MOD_ID, "block/light_10"),
            Identifier.of(MOD_ID, "block/light_11"),
            Identifier.of(MOD_ID, "block/light_12"),
            Identifier.of(MOD_ID, "block/light_13"),
            Identifier.of(MOD_ID, "block/light_14"),
            Identifier.of(MOD_ID, "block/light_15")
    );
    public static boolean showLights = false;
    public static final Identifier STRUCTURE_VOID_ID = Identifier.of(MOD_ID, "block/structure_void");
    public static boolean showStructureVoid = false;
    
    @Override
    public void onInitialize() {
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.BARRIER, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.LIGHT, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.STRUCTURE_VOID, RenderLayer.getCutoutMipped());
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            showBarriers = shouldRender(client, Items.BARRIER);
            showLights = shouldRender(client, Items.LIGHT);
            showStructureVoid = shouldRender(client, Items.STRUCTURE_VOID);
        });
    }
    
    private boolean shouldRender(MinecraftClient client, Item item) {
        final ClientPlayerInteractionManager interactionManager = client.interactionManager;
        final ClientPlayerEntity player = client.player;
        if(interactionManager == null || player == null) {
            return false;
        }
        final boolean IS_HOLDING_ITEM = player.getMainHandStack().getItem() == item || player.getOffHandStack().getItem() == item;
        return interactionManager.getCurrentGameMode().isCreative() && IS_HOLDING_ITEM;
    }
}
