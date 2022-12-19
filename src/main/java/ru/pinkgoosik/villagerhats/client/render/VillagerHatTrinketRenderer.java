package ru.pinkgoosik.villagerhats.client.render;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import ru.pinkgoosik.villagerhats.item.VillagerHat;

@Environment(EnvType.CLIENT)
public class VillagerHatTrinketRenderer implements TrinketRenderer {

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(contextModel instanceof BipedEntityModel<? extends LivingEntity> biped) {
            if(stack.getItem() instanceof VillagerHat hat) {
                ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
                double height = hat.getHeight();
                float size = hat.getSize();

                matrices.push();
                biped.head.rotate(matrices);
                matrices.translate(0D, -1D, 0D);
                matrices.translate(0D, -(height), 0D);
                matrices.scale(size, size, size);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0F));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));

                var model = new ModelIdentifier("villager-hats", hat.getHatName(), "inventory");
                itemRenderer.renderItem(stack, ModelTransformation.Mode.NONE, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, itemRenderer.getModels().getModelManager().getModel(model));
                matrices.pop();
            }
        }
    }

}
