package dev.callmeecho.hollow.client.render.entity;

import dev.callmeecho.hollow.main.block.entity.JarBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

public class JarBlockEntityRenderer implements BlockEntityRenderer<JarBlockEntity> {
    public JarBlockEntityRenderer(BlockEntityRendererFactory.Context ignoredCtx) { }

    @Override
    public void render(JarBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        final MinecraftClient client = MinecraftClient.getInstance();
        World world = client.world;
        ItemRenderer renderer = client.getItemRenderer();
        DefaultedList<ItemStack> items = blockEntity.getItems();
        if (items.isEmpty() || world == null) return;
        
        matrices.push();
        matrices.translate(0.5F, 0.05F, 0.5F);
        matrices.scale(0.45F, 0.45F, 0.45F);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
        for (ItemStack item : items) {
            matrices.translate(0.0F, 0.0F, -0.0625F);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(22.5F));
            renderer.renderItem(item, ModelTransformationMode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, null, 0);
        }
        matrices.pop();
    }
}
